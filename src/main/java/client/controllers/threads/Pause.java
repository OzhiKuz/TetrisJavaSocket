package client.controllers.threads;

import server.GameI;

import java.rmi.RemoteException;

public class Pause extends Thread {

    private int speed;
    private GameI stub;
    private boolean result = true;
    private boolean finished = false;

    public boolean isFinished() {
        return finished;
    }

    public boolean isResult() {
        return result;
    }

    public Pause(int speed, GameI stub) {
        this.speed = speed;
        this.stub = stub;
    }


    @Override
    public void run() {
        try { result = stub.makeMove(1,0);
          this.sleep(5000);

          finished = true;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
