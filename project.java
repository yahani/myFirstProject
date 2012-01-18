import java.util.logging.Level;
import java.util.logging.Logger;

class ant extends Thread{
private int id;
private boolean outside;
private antHill hill;

    public ant(int id, antHill hill) {
        this.id = id;
        this.hill = hill;
        this.outside=true;
    }

    public antHill getHill() {
        return hill;
    }

    public int getID() {
        return id;
    }

    public boolean isOutside() {
        return outside;
    }

    public void setOutside(boolean outside) {
        this.outside = outside;
    }

    public void tryToEnter(){
        if(hill.aquire()){
            outside=false;
            System.out.println("ant "+id+" is entering...");
            try {
                sleep(id*100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ant.class.getName()).log(Level.SEVERE, null, ex);
            }
            hill.release();
        }

    }

    public void run(){
        while(outside){
            tryToEnter();
        }
    }
}


class antHill {
    boolean available;

    public antHill() {
        available=true;
    }


   public synchronized  boolean aquire() {
        if(!available){
            return false;
        }
        else{
            available=false;
            return true;
        }
    }

  public  void release() {
        if(!available){
            available=true;
        }
    }

}


public class antColony {
private static final int colSize=10;
private ant[] antzz;
private antHill hill;

    public antColony() {
        hill=new antHill();
        antzz=new ant[colSize];
    }

public void antActivity(){
    for(int i=0;i<colSize;i++){
        antzz[i]=new ant(i,hill);
    }
    for(int i=0;i<colSize;i++){
        antzz[i].start();
    }
}

public static void main(String args[]){
    antColony col=new antColony();
    col.antActivity();
}
}
