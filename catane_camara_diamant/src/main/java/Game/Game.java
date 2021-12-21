package Game;

public class Game implements Runnable{
    private final Player[] players;
    private GameDisplay display;
    private Thread thread;
    private boolean running=false;

    public Game(Player[] players){
        this.players=players;
    }

    public void init(){
        display =new GameDisplay();
    }

    private void update(){

    }

    private void render(){

    }

    @Override
    public void run() {
        init(); 

        while(running){ //Tant qu'il n'y pas de gagnant.
            update(); 
            render();
        } 

        stop();
    }

    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread =new Thread(this);
        thread.start();
    }
    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();    
        }
    }

    //Getters et Setters
    public Player[] getPlayers() {return players;}
    public GameDisplay getDisplay() {return display;}
    public void setDisplay(GameDisplay display) {this.display = display;}
    public Thread getThread() {return thread;}
    public void setThread(Thread thread) {this.thread = thread;}
    public boolean isRunning() {return running;}
    public void setRunning(boolean running) {this.running = running;} 
}
