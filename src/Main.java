import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

class Semaphore{
    int value ;
    Semaphore(int value)
    {
        this.value = value;
    }
    void wait(Device d) throws InterruptedException {
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
    void signal(Device d)
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
    private final Semaphore semaphore;
    private final List<Device> connections;
    private int maxConnections;


    //----------------------------------------------------------------

    Router(int n){
        this.semaphore = new Semaphore(maxConnections);
        this.maxConnections = n;
        this.connections = new ArrayList<Device>(maxConnections);
    }

    //----------------------------------------------------------------

    void occupyConnection(Device device)throws InterruptedException{
        if(connections.size() < maxConnections){
            connections.add(device);
        }
        semaphore.wait(device);
        sleep(100);

    }

    //----------------------------------------------------------------

    void releaseConnection(Device device){
        
        if(!connections.isEmpty()){
            connections.remove(device);
        }
        semaphore.signal(device);
        notify();
    }
}

//----------------------------------------------------------------------------------------------------------------------
class Device {

}
//----------------------------------------------------------------------------------------------------------------------
class Network{

}

//----------------------------------------------------------------------------------------------------------------------

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}