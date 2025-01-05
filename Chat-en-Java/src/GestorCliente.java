import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GestorCliente implements Runnable {

    // Alamacena los mensajes y los distruye entre los distintos clientes
    public static ArrayList<GestorCliente> gestorClientes= new ArrayList<>();
    // Conexión
    private Socket socket;
    // Lee los mensajes que han sido enviados desde el cliente
    private BufferedReader bufferedReader;
    // Manda mensajes a los clientes, que a su vez son mensajes enviados por otros clientes
    private BufferedWriter bufferedWriter;
    // Nombre del Usuario
    private String nombreUsuario;


    public GestorCliente(Socket socket) {
        try{
            // Conexión
            this.socket = socket;
            // Los mensajes que manda
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // Los mensajes que recibe
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Nombre del usuario que lee de los mensajes de la clase cliente
            this.nombreUsuario = bufferedReader.readLine();

            synchronized (gestorClientes) {
                // Almacena los mensajes en el arrayList
                gestorClientes.add(this);
            }

            emisionMensaje("SERVIDOR: " + nombreUsuario + " ha entrado en el chat!");
        }catch (IOException e){
            cierraConex(socket, bufferedReader, bufferedWriter);
        }
    }


    // Administra cada hilo de forma separada
    // Escucha cada mensaje de forma separada
    // Procedente del servidor
    @Override
    public void run() {
        String mensajeDesdeCliente;

        while (socket.isConnected()) {
            try{
                mensajeDesdeCliente = bufferedReader.readLine();

                /*if (mensajeDesdeCliente.equalsIgnoreCase("exit")) {
                    emisionMensaje("SERVIDOR: " + nombreUsuario + " ha salido del chat!");
                    cierraConex(socket, bufferedReader, bufferedWriter);
                    break;
                }*/

                emisionMensaje(mensajeDesdeCliente);

            } catch (IOException e) {
                cierraConex(socket, bufferedReader, bufferedWriter);
                // Si el cliente se desconecta -> break
                break;
            }
        }

    }

    // Almacena y gestiona los mensajes recibidos para mandarlos a los demás clientes
    private void emisionMensaje(String mensajeQueEnviar) {

        synchronized (gestorClientes) {

            for (GestorCliente gestorCliente : gestorClientes) {
                try {
                    if (!gestorCliente.nombreUsuario.equals(nombreUsuario)) {
                        gestorCliente.bufferedWriter.write(mensajeQueEnviar);
                        // Salto de Línea
                        gestorCliente.bufferedWriter.newLine();
                        // Llenar completamente el buffer aunque la conexión no esté cerrada
                        gestorCliente.bufferedWriter.flush();
                    }
                } catch (IOException e) {
                    cierraConex(socket, bufferedReader, bufferedWriter);
                }
            }
        }
    }

    // Elimina los clientes del ArrayList
    public void eliminarGestorCliente() {
        gestorClientes.remove(this);
        emisionMensaje("El usuario " + nombreUsuario + " ha sido dejado el chat!");
    }

    public void cierraConex(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        eliminarGestorCliente();
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }







}
