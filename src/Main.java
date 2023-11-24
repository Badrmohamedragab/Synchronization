import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            wait();
        }
        else {
            System.out.println(d.name +'('+d.type+')' + " arrived");
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
    private final List<Device> connections;
    private int maxConnections;

    Router(int n){
        this.maxConnections = n;
        this.semaphore = new Semaphore(n);
        this.connections = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            connections.add(null);
        }
    }
    int occupyConnection(Device device)throws InterruptedException{
        for (int i = 0; i < maxConnections; i++) {
            if (connections.get(i) == null) {
                connections.set(i, device);
                device.connectionID = i + 1;
                break;
            }
        }
        semaphore.wait(device);
        return device.connectionID;
    }


    void releaseConnection(Device device){
        connections.set(device.connectionID - 1, null);
        System.out.println("Connection " + device.connectionID + ": " + device.name + " logged out");
        semaphore.signal(device);
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
            int connectionInd = router.occupyConnection(this);
            System.out.println("Connection " + connectionInd + ": " + this.name + " Occupied");
            System.out.println("Connection " +  connectionInd + ": " + this.name + " login");
            System.out.println("Connection " +  connectionInd + ": " + this.name + " performs online activity");
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

}
//----------------------------------------------------------------------------------------------------------------------

