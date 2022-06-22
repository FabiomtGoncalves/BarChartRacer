package pt.ipbeja.po2.chartracer.model;

public class MultithreadThing extends Thread{

    @Override
    public void run(){
        for (int i = 0; i < 50; i++) {
            System.out.println(i);

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }

        }
    }

}
