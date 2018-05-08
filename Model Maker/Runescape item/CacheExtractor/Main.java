import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.io.*;

public class Main {
    private UnPack pack = new UnPack();
    public Main() {
		try{
		}
		catch(Exception e){}
        System.out.println("Runescape Model Grabber");
        System.out.println("Thanks to Silabsoft");
        System.out.println("~~~~~~~~~~");
        findVersion();
        System.out.println("~~~~~~~~~~");
        try {
            for(int i = new File("./Models/").listFiles().length; i < 9999999; i++){
//		if(i > min-0 && i < max) {//for grabbing a range of models
//		if(i == ) {//for grabbing 1 model
		if(i > 0) {//grabs all models
                System.out.println("Sending request for Model: "+i);
                addRequest( 7, i);
                byte b[] =  pack.unpack(download().buffer);
                if(pack.unpacked) {
                    savemodel(i,b);
                    System.out.println("Model Saved");
                }else{
                    System.out.println("Model Grabbing Complete");
                    break;
                }
		}
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void savemodel(int id, byte[] b){
            try {
            FileOutputStream fileoutputstream = new FileOutputStream("./Models/"+id+".dat");
            fileoutputstream.write(b, 0, b.length);
            fileoutputstream.close();
        } catch(Throwable _ex) {}
}

    public static void main(String[] args) {
        new Main();
    }

    public void findVersion() {
        System.out.println("Grabbing Cache Version.");
        while (true) {
            try {
                System.out.print("Trying: " + version);
                if (connect(address, port, version) == false) {
                    System.out.println(" Rejected.");
                    version++;
                } else {
                    System.out.println(" Accepted.");
                    break;
                }
            } catch (Exception _ex) {
                System.out.println(" Rejected.");
            }
        }
    }

    public boolean connect(String server, int port, int version) throws IOException {
        socket = new Socket(server, port);
        input = socket.getInputStream();
        output = socket.getOutputStream();
        output.write(15);
        output.write(0);
        output.write(0);
        output.write(version >> 8);
        output.write(version);
        output.flush();
        int r = input.read();
        if (r == 0) {
            return true;
        } else {
            close();
            return false;
        }
    }

    public void addRequest(int c, int i) throws IOException {
		output.write(0);
		output.write(c);
		output.write(i >> 8);
		output.write(i);
		output.flush();
	}
    public CacheChunk download() throws IOException {
        CacheChunk chunk = new CacheChunk();
        byte dHead[] = new byte[3];
        byte jHead[] = new byte[5];
        input.read(dHead);
        input.read(jHead);
        chunk.cache = dHead[0];
        chunk.type = jHead[0];
        chunk.index = ((dHead[1] & 0xff) << 8) + (dHead[2] & 0xff);
        int s = ((jHead[1] & 0xff) << 24);
        s += ((jHead[2] & 0xff) << 16);
        s += ((jHead[3] & 0xff) << 8);
        s += (jHead[4] & 0xff) + 5;
        if (chunk.type == 2 || chunk.type == 1)
            s += 4;
        chunk.buffer = new byte[s];
        System.arraycopy(jHead, 0, chunk.buffer, 0, 5);
        int c = 5;
        int m = 8;
        while (c < s) {
            if (m == 512) {
                m = 1;
                input.read();
            } else {
                chunk.buffer[c] = (byte) input.read();
                c++;
                m++;
            }
        }
        return chunk;
    }

	public void close() {
		try{
			output.close();
			input.close();
			socket.close();
		} catch (Exception _ex) {}
	}
    private int version = 520;
    private static String address = "world169.runescape.com";
    private static int port = 43594;

    private Socket socket;
    private InputStream input;
    private OutputStream output;
}

