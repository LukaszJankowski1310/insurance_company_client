import java.io.*;
import java.net.Socket;

public class Client {
    private final Socket clientSocket;

    private final BufferedWriter bufferedWriter;
    private final BufferedReader bufferedReader;

    Client(Socket socket) throws IOException {
        this.clientSocket = socket;
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
        bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
    }

    public void writeMessage(String message)  {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            closeAll(clientSocket, bufferedReader, bufferedWriter);
        }

    }

    public String readMessage(){
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void closeAll(Socket clientSocket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        try {
            clientSocket.close();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
