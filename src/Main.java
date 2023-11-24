import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static java.lang.Thread.sleep;

class Semaphore{
    int value ;
    Semaphore(int value)
    {
        this.value = value;

    }
    synchronized void wait(Device d) throws InterruptedException {
        value--;
        if(value < 0)
        {
            System.out.println(d.name +'('+d.type+')' + " arrived and waiting");
            Network.write(d.name +'('+d.type+')' + " arrived and waiting");
            wait();
        }
        else {
            System.out.println(d.name +'('+d.type+')' + " arrived");
            Network.write(d.name +'('+d.type+')' + " arrived");
        }

    }
    synchronized void signal(Device d)
    {
        value++;
        if (value <= 0)
        {
            notify();
        }
    }


}

//  --------------------------------------------------------------------------------------------------------------------
class Router{
        public final Semaphore semaphore;
        private final boolean[] connections;
        private final int maxConnections;

        Router(int n){
            this.maxConnections = n;
            this.semaphore = new Semaphore(n);
            this.connections = new boolean[n];
        }
    int occupyConnection(Device device)throws InterruptedException{
        semaphore.wait(device);
        for (int i = 0; i < maxConnections; i++) {
            if (!connections[i]) {
                connections[i] = true;
                device.connectionID = i + 1;
                break;
            }
        }
        return device.connectionID;
    }


    void releaseConnection(Device device) throws InterruptedException {
        semaphore.signal(device);
        connections[device.connectionID-1] =  false;
        System.out.println("Connection " + device.connectionID + ": " + device.name + " logged out");
        Network.write("Connection " + device.connectionID + ": " + device.name + " logged out");
        sleep((long) (Math.random()* 1000));

    }

}

//----------------------------------------------------------------------------------------------------------------------
class Device extends Thread{
    String type, name;
    int connectionID;
    Router router;
    Device(String name, String type, Router router){
        this.name = name;
        this.type = type;
        this.router = router;
        this.connectionID = 1;
    }
    public void run() {
        try{
            this.connectionID = router.occupyConnection(this);
            sleep((long) (Math.random()* 1000));
            System.out.println("Connection " + connectionID + ": " + this.name + " Occupied");
            Network.write("Connection " + connectionID + ": " + this.name + " Occupied");


            sleep((long) (Math.random()* 1000));
            System.out.println("Connection " +  connectionID + ": " + this.name + " login");
            Network.write("Connection " +  connectionID + ": " + this.name + " login");
            System.out.println("Connection " +  connectionID + ": " + this.name + " performs online activity");

            Network.write("Connection " +  connectionID + ": " + this.name + " performs online activity");
            sleep((long) (Math.random()* 1000));
            router.releaseConnection(this);
        }
        catch(InterruptedException ex){

        }
    }
}
//----------------------------------------------------------------------------------------------------------------------
class Network{
    public static void main(String[] args) throws InterruptedException {
        int numOfConnections, numOfDevices;
        ArrayList<Device> devices = new ArrayList<>();
        System.out.println("What is the number of WI-FI Connections?");
        Scanner scanner= new Scanner(System.in);
        numOfConnections = scanner.nextInt();
        Router router = new Router(numOfConnections);

        System.out.println("What is the number of devices Clients want to connect?");
        numOfDevices = scanner.nextInt();

        for(int i = 0; i < numOfDevices ; i++)
        {
            String name, type;
            name = scanner.next();
            type = scanner.next();
            Device device = new Device(name, type, router);
            devices.add(device);
        }
        for (int i = 0 ; i < devices.size(); i++ )
        {
            devices.get(i).start();
        }
    }
    public static void write(String content){
        try{
            Path path = Paths.get(System.getProperty("user.dir") + "\\logs.txt") ;
            File file = new File(path.toString()) ;

            if (!file.exists()){
                if (!file.createNewFile()){
                    System.out.println("Error in creating the file");
                }
            }
            Files.write(path, (content + '\n').getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}


//----------------------------------------------------------------------------------------------------------------------


