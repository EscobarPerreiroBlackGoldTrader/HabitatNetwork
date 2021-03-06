/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitatnetwork;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.ALLBITS;
import java.io.IOException;
import java.io.PipedWriter;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.event.MouseInputAdapter;
//==============================================================================
/**
 *
 * @author iUser
 */
public class Habitat extends /*JApplet*/JPanel  /*implements Serializable*/ {
    private Timer m_timer = new Timer(); //private javax.swing.Timer swTimer ;//= new javax.swing.Timer();
    //private Timer gUpd_timer = new Timer(); // �������� ���������� "������"
    private Updater m_updater; 

    
    
    CarMan /*Thread*/ ThreadCarManager;
    MotoMan /*Thread*/ ThreadMotoManager;
    
    ExecutorService execCars; 
    ExecutorService execMoto;
    
    private boolean pausedMoto = false;
    private boolean pausedCar = false;

    public void setPausedMoto(boolean pausedMoto) {
        this.pausedMoto = pausedMoto;
    }

    public void setPausedCar(boolean pausedCar) {
        this.pausedCar = pausedCar;
    }

    public synchronized boolean isPausedMoto() {
        return pausedMoto;
    }

    public synchronized boolean isPausedCar() {
        return pausedCar;
    }
    
    
    
    
    private double m_time = 0;
    private double p1 = 0.20; // ����������� ��������� ���������
    private double p2 = 0.30; // ����������� ��������� ������
    private int period = 100; // ������� ���������� �������
    private boolean emul_progress = false; // ������������� �������� T
    private boolean showtime = false; // ������������� �������� B

    public boolean isEmul_progress() {
        return emul_progress;
    }
    
    private long startTime = 0, updaterPauseShift = 0, updaterPauseBeg =0;//, currentTime = 0;

    //-----------------------------------------
    public void setVel_count(int vel_count) {
        this.vel_count = vel_count;
    }

    public int getVel_count() {
        return vel_count;
    }
    
    private int vel_count = 0;
    //-----------------------------------------
    //int vel_shown = 0;

    //-----------------------------------------
    public int getCar_count() {
        return car_count;
    }


    public void setCar_count(int car_count) {
        this.car_count = car_count;
    }

    private int car_count = 0;
    //-----------------------------------------
    
    //-----------------------------------------
    public void setMoto_count(int moto_count) {
        this.moto_count = moto_count;
    }
    public int getMoto_count() {
        return moto_count;
    }
    
    private int moto_count = 0;
    //-----------------------------------------
    
    final int CLOCK_RATE = 42; // ������� ����������� ������ (�������� � ������������)
    
    BufferedImage motopic = null;
    BufferedImage carpic = null; 
    Image offScreenImage = null;
    
    /*volatile ArrayList*/CopyOnWriteArrayList<BaseAI> lst; //������ �� ������ ��������
    //private boolean firstRUN = true; // ����� ������� ���������� (���������� ��� ���������� ��������)
    private boolean picLoaded=false; // �������� �������� �����������
    private String m_FileName1 = "motopic.png";
    private String m_FileName2 = "carpic.png";
    //private String PARAM_string_1 = "fileName";
    
    
    transient javax.swing.GroupLayout layout;
    private void initComponents() {

        layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        GroupLayout.ParallelGroup pg = layout.createParallelGroup();
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        
        
    } 
//==============================================================================
    void setMotoPriority(int prior) {
        ThreadMotoManager.setPriority(prior);
    }
//==============================================================================
    void setCarPriority(int prior) {
        ThreadCarManager.setPriority(prior);
    } 
//==============================================================================
     synchronized CopyOnWriteArrayList<BaseAI> get_lst_copy() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        CopyOnWriteArrayList<BaseAI> cp = new CopyOnWriteArrayList<>();
        for (BaseAI next : lst) {
            cp.add(next);
        }
        return  cp;
    }
//==============================================================================
    synchronized void update_lst(CopyOnWriteArrayList<BaseAI> income_lst) {
        // � ��� ���� �������� �������� 
        for(BaseAI item: income_lst){
            lst.add(item);
            vel_count++;
            if(item instanceof Car)car_count++;
            if(item instanceof Moto)moto_count++;
        }
        repaint();
    }
//==============================================================================
    private class Updater extends TimerTask /*implements Serializable*/{
        private Habitat m_aplet = null;
        private boolean m_firstRun = true; // ������ �� ������ ������ run()?
        private long m_startTime = 0; // ����� ������ 
        private long m_lastTime = 0;  // ����� ���������� ����������
        private long pauseShift = 0;
        private long pauseBeg = 0;
        private boolean paused = false;
        
        public Updater(Habitat applet){
            m_aplet = applet;
        }
        
        public void setPauseShift(long pauseShift){
            this.pauseShift = pauseShift;
        }
        
        public long getPauseShift(){
            return this.pauseShift;
        }
        
        public void setPauseBeg(long pauseBeg){
            this.pauseBeg = pauseBeg;
        }
        
        public long getPauseBeg(){
            return this.pauseBeg;
        }
        
        
        public void go(){
            m_firstRun = true;
            m_lastTime = 0;
            m_startTime = 0;
            pauseShift = 0;
            pauseBeg = 0;
        }
        
        public void finish(){
            m_firstRun = true;
            m_lastTime = 0;
            m_startTime = 0;
            pauseShift = 0;
            pauseBeg = 0;
        }
        
        public void drop(){
            m_firstRun = true;
            m_lastTime = 0;
            m_startTime = 0;
            pauseShift = 0;
            pauseBeg = 0;
        }
        
        public void set_m_startTime(long atTime){
            this.m_startTime = atTime;
            m_lastTime = m_startTime;
            m_firstRun = false; // ������������� ������ �������
        }
        
        public void pauseBeg(){
            pauseBeg = System.currentTimeMillis();
            paused = true;
        }
        
        public void pauseEnd(){
            pauseShift += System.currentTimeMillis() - pauseBeg;
            paused = false;
            
        }
        
        public long get_m_startTime(){return m_startTime;}
        //public long get_currentTime(){return (m_lastTime - m_startTime);}
        @Override
        public void run(){
            
            
            
            if(!paused){// ���� �� �� �����
                if(m_firstRun){
                    m_startTime = System.currentTimeMillis();
                    m_lastTime = m_startTime;
                    m_firstRun = false;
                }
                 
                 long currentTime  = System.currentTimeMillis();
                 
                 //����� ��������� �� ������, � ��������.
                 double elapsed = ((currentTime - m_startTime)- pauseShift) / 1000.0; // pauseShift dont work properly
                 
                 //�������� ����������
                 m_aplet.Update(elapsed/*, frameTime*/);
                 
                 m_lastTime = currentTime; //�����
            
            }// endif    
                 
        }//end run()
    }
//==============================================================================
    public int getPeriod(){return this.period;}
//==============================================================================   
    public Habitat(){
                
        System.out.println("����������� Habitat �������");
        initComponents();
        
        execCars = Executors.newCachedThreadPool();
        execMoto = Executors.newCachedThreadPool();

          try{
              motopic = ImageIO.read(getClass().getResource("motopic.png"));
          }catch(IOException e){
              //e.printStackTrace();
          }
    
          try{
              carpic = ImageIO.read(getClass().getResource("carpic.png"));
          }catch(IOException e){
              
          }
          
        
        //motopic = getImage(getCodeBase(), "images/motopic.png");
        //if(carpic == null)carpic = getImage(getDocumentBase(), "carpic.png");
        
          lst = new /*ArrayList<>*/CopyOnWriteArrayList<>(); // �������� ����� �������
        //---------------------------------
        // ���������� ������� �� ���� 
        //(�������� ����� � ������ �����. 
        // ����� ����� ������� ���� ����� �������� �����, 
        // ����� �� ����� �������������� ������� �� ����������.)
        
      MouseInputAdapter pm;  
      pm = new MouseInputAdapter() { 
       @Override
       public void mousePressed(MouseEvent e) { 
//             x=e.getX(); y=e.getY(); 
//             System.out.println(x); 
//             repaint(); 
       }}; 
       this.addMouseListener(pm);
        
        //---------------------------------
        KeyAdapter pk;
        pk = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e){
            System.out.println(e);
            int keycode = e.getKeyCode();
            
            switch(keycode){
                case KeyEvent.VK_B: // ��������� ���������
                    start_sim();
                    break;
                case KeyEvent.VK_E: //���������� ���������
                    
                    stop_sim();
                    break;
                case KeyEvent.VK_T:
                    
                    trig_timer();
                    break;
            }
            
        } 
    };
        
        ThreadCarManager = new CarMan(this);
        ThreadMotoManager = new MotoMan(this);
        
        
        ///--- ���������� ��� ������������ �������� ����� �������� ������ �������� ---
        setPausedCar(true);
        execCars.execute(ThreadCarManager);
        setPausedMoto(true);
        execMoto.execute(ThreadMotoManager);
        //----------------------------------------------------------------------------
        
        
        //pause_sim();
        
        this.addKeyListener(pk);
        Init();
    }
//==============================================================================
    // ������ ����� ����������� ��������� ���������
    public void setP_moto(String s){
        String buf = new String();
        switch(s){
            case "0%":
                System.out.println("������ ������ 0%: " + s);
                buf = s.substring(0, 1);
                break;
            case "100%":
                System.out.println("������ ������ 100%: " + s);
                buf = s.substring(0, 3);
                break;
            default:
                System.out.println("������ ������ ������ : " + s);
                buf = s.substring(0, 2);
                break;
        }
        
        this.p1 = (Double.parseDouble(buf))/100;
        System.out.println(p1);
    }
//==============================================================================
    // ������ ����� ����������� ��������� ����������
    public void setP_car(String s){
        String buf = new String();
        switch(s){
            case "0%":
                System.out.println("���� ������ 0%: " + s);
                buf = s.substring(0, 1);
                break;
            case "100%":
                System.out.println("���� ������ 100%: " + s);
                buf = s.substring(0, 3);
                break;
            default:
                System.out.println("���� ������ ������ : " + s);
                buf = s.substring(0, 2);
                break;
        }
        
        this.p2 = (Double.parseDouble(buf))/100;
        System.out.println(p2);
    }
//==============================================================================
    public String getStatistic(){
        String s = "����� ���������:\n" +
                       "����� " + car_count +
                       "\n���������� " + moto_count +
                       "\n����� ������������ ������� " + vel_count +
                       "\n�� ����� " + m_time + " ������";
        return s;
    }
//==============================================================================
    public void start_sim(){ // ��������� ���������
        System.out.println("B is pressed");
        emul_progress = true;
        //repaint();
        setPausedCar(false);
        setPausedMoto(false);
        unfreezeCar();
        unfreezeMoto();
        
        m_updater.go();
    }
//==============================================================================
    public void stop_sim(){ // ���������� ���������
        System.out.println("E is pressed");
        emul_progress = false;
        //vel_shown = 0;
        vel_count = 0;
        car_count = 0;
        moto_count = 0;
        
        //��� ������ ���������� �������� � �����������
        
        //ThreadCarManager.workDone();
        //ThreadMotoManager.workDone();
        setPausedCar(true);
        setPausedMoto(true);
        
        //((CarMan)ThreadCarManager).going = false;
        //((MotoMan)ThreadMotoManager).going = false;
        
        lst.clear();
        m_updater.finish();
        //****************************//
        repaint();
    }
//==============================================================================
    public boolean pause_sim(){
        System.out.println("��������� ����� pause_sim()");
        if(emul_progress){//�����
            //currentTime = m_updater.get_currentTime();
            //startTime = m_updater.get_m_startTime();
            m_updater.pauseBeg();
            
            
            setPausedMoto(true);
            setPausedCar(true);
            
            
        }else{// �����������
            unfreezeCar();
            unfreezeMoto();            
            setPausedMoto(false);
            setPausedCar(false);
            m_updater.pauseEnd();
            //m_updater.drop();
            //m_updater.set_m_startTime(startTime);
        }
        
        return (emul_progress = !emul_progress); // ������� ��������� ��������
    }
//==============================================================================
    public void hold_sim(){
        emul_progress = false;
    }
//==============================================================================
    public void unhold_sim(){
        emul_progress = true;
    }
//==============================================================================
    public void set_period(int val){ //*********************************//
        this.period = val;
          startTime = m_updater.get_m_startTime(); // ��������� ����� ������ ������� ����� ��������� 
          updaterPauseShift = m_updater.getPauseShift();// ���������
          updaterPauseBeg = m_updater.getPauseBeg();
          
          m_updater.cancel();
          m_timer.purge();// no ex
          m_timer.cancel();// no ex
          
        //swTimer.
       
        m_timer = new Timer();
        m_updater = new Updater(this);
        m_updater.set_m_startTime(startTime);
        m_updater.setPauseBeg(updaterPauseBeg);
        m_updater.setPauseShift(updaterPauseShift);
        m_timer.schedule(m_updater, 0, period); //!!!!!this ex!!!!
    }
//==============================================================================
    public boolean trig_timer(){ // ��������/������ ������
        System.out.println("T is pressed");
        showtime = !showtime;
        repaint();
        return showtime;
    }
//==============================================================================
    public void set_timer_show(boolean state){
        showtime = state;
        repaint();
    }
//==============================================================================
    public boolean timerValueState(){
        return showtime;
        
    }
//==============================================================================   
synchronized void unfreezeMoto(){
    
    ((MotoMan)ThreadMotoManager).unfreeze();
    
}
//==============================================================================   
synchronized void unfreezeCar(){
    
    ((CarMan)ThreadCarManager).unfreeze();
}
//==============================================================================    
    @Override
    public boolean imageUpdate(Image img, int infoflags,int x, int y,int w, int h){
        
        if(infoflags == ALLBITS){
            picLoaded = true;
            repaint();
            return false; // ������ ����� update() �� ��������
        }else{
            return true;
        }
    }
//==============================================================================    
    private void Init(){
        // ������ ����� ���������� ������ 100��
        m_updater = new Updater(this);
        
        m_timer.schedule(m_updater, 0, period/*100*/);

    }
//==============================================================================    
    public void Update(double elapsedTime/*, double frameTime*/){
       if(emul_progress){
           m_time = elapsedTime;
       }else{

       }
       
       if(emul_progress){
           double p0 = Math.random();
           
           if(p0 <= p1){ // �������� ��������
               
               Moto m = new Moto(this);
               //if(!m.setPic(motopic)) System.err.println("motoico missed");
               m.setX( (int)((getWidth()/1.2) * Math.random()) );
               m.setY( (int)((getHeight()/1.2) * Math.random()) ); 

               lst.add(m);
               
               ++vel_count;
               ++moto_count;
               
           }
           
           if(p0 <= p2){ // ��������� ������
               
               Car c = new Car(this);
               //if(!c.setPic(carpic)) System.err.println("carico missed");
               c.setX( (int)((getWidth()/1.2) * Math.random()) );
               c.setY( (int)((getHeight()/1.2) *Math.random()) );
               
               lst.add(c);
            
               ++vel_count;
               ++car_count;
               
           }    
       }
       
       //this.repaint(); //!!! ����������� ������ ������ � ������ gUpdater
    }
    
//==============================================================================    
    @Override
    public void paint(Graphics g){
        
        
        // �������� ������������ ������
        int width = getSize().width,heigth = getSize().height;    
        offScreenImage = createImage(width, heigth);
        // ��������� ��������� ������������ ������
        Graphics offScreenGraphics = offScreenImage.getGraphics();
        
        //������� ������      
            offScreenGraphics.setColor(Color.white);
            offScreenGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
            offScreenGraphics.setColor(Color.GREEN);
            offScreenGraphics.setFont(new Font("Tahoma",Font.BOLD,12));
            //firstRUN = false;

        String str = "Time = " + Double.toString(m_time); //��������� ������� �� �������
        
        if(showtime)offScreenGraphics.drawString(str, 395, getHeight()-10); // ����������� �������        
        
        for(Iterator<BaseAI> it = lst.iterator();it.hasNext();){
            BaseAI next = it.next();
            BufferedImage img;
            
            if(next instanceof Car){
                img = carpic;
            }else{
                img = motopic;
            }
            
            offScreenGraphics.drawImage(img,next.getX(),next.getY(),this);
        }

        offScreenGraphics.setColor(Color.red);
        offScreenGraphics.setFont(new Font("Arial",Font.BOLD,14));
        offScreenGraphics.drawString("vel_count = " + vel_count, 10, getHeight()-10);
        offScreenGraphics.drawString("car_count = " + car_count, 135, getHeight()-10);
        offScreenGraphics.drawString("moto_count = " + moto_count, 255, getHeight()-10);
        //offScreenGraphics.drawString("vel_shown = " + vel_shown, 120, 140);
        
        
        //--------
        if(/*picLoaded*/true){
           g.drawImage(offScreenImage, 0, 0, null); 
        }else{
            //showStatus("Loading image");
        }
        
        //--------
        
    }    

private PipedWriter pw = new PipedWriter();

    public PipedWriter getWr_Stream() {
        return pw;
    }
    
    public void write_to_console(String s){
        if(s == null){
            for(int k=0; k< 10;++k)
                try {
                    pw.write(String.valueOf(k));
                    System.out.println("Writing " + k);
                } catch (IOException ex) {
                    Logger.getLogger(Habitat.class.getName()).log(Level.SEVERE, null, ex);
                }
        }else {
            try {
                
                pw.write(s);
            } catch (IOException ex) {
                Logger.getLogger(Habitat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }

    public void ReduceMotoCountBy(int val){
        synchronized(lst){
            System.out.println("����� � ������������� � ������");
            Integer ostatok = moto_count % val;
            System.out.println("������� �� ������� " + ostatok);
            Integer new_mc = moto_count - ostatok;
            
            int c=0; 
            for(Iterator<BaseAI> it = lst.iterator(); it.hasNext() && c < ostatok; ){
                BaseAI next = it.next();
                if(next instanceof Moto){
                    if(lst.remove(next)){
                        System.out.println("������ ��������");
                        write_to_console("������ ��������\n");
                    }
                    ++c;
                }
            }
            moto_count = new_mc;
        }
    }
}
