import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private ServerSocket servidor;

    public Servidor(ServerSocket servidor) {
        this.servidor = servidor;
    }



    public static void main(String[] args) throws IOException {
        int host = 0;
        try {
            // Número de Puerto
            host = 1234;
            // Creamos el Socket del Servidor
            // Es el puerto por el que estará escuchando a los clientes
            ServerSocket servidorSocket = new ServerSocket(host);
            // Instanciamos el objeto servidor y le pasamos el servidorSocket
            Servidor servidor = new Servidor(servidorSocket);
            // Iniciamos el Servidor
            servidor.iniciarServidor();
        } catch (BindException e) {
            System.out.println("No se pudo enlazar al puerto: " + host + ". El puerto ya está en uso: ");
        } finally {
            System.out.println("Servidor cerrado.");
        }

    }



    //Mantiene el Servidor encendido
    public void iniciarServidor() {

        try{
            // Indica que el Servidor arrancó
            System.out.println("Iniciando servidor...");
            // Mientras el Servidor este encendido
            while(!servidor.isClosed()){
                // Prepara al servidor para que los clientes se conecten
                // El programa se detendrá esté punto hasta que un cliente se conecte
                Socket socket = servidor.accept();
                System.out.printf("Un nuevo cliente se ha conectado: %s\n", socket.getInetAddress());
                // Creamos el objeto
                GestorCliente gestorCliente = new GestorCliente(socket);
                // Administra los hilos en la clase GestorCliente
                // entre servidor y cliente
                Thread hilo = new Thread(gestorCliente);
                // Iniciamos el hilo
                hilo.start();

            }
        }catch (IOException e){
            CerrarServidor();
        }
    }

    public void CerrarServidor() {
        try{
            if(servidor != null){
                servidor.close();
            }
        } catch (IOException e) {
            System.out.printf("Servidor desconectado\n %s\n", e.getMessage());
        }
    }


}
