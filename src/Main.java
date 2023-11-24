import java.util.List;

class Router{
    List<Device> connections;
}

//  --------------------------------------------------------------------------------------------------------------------

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