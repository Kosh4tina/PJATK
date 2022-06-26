public class Server {
    public static void main(String[] args){
        for(int i = 0; i<args.length; i++) {
            new Thread(new ServerThread(Integer.parseInt(args[i]))).start();
        }
    }
}