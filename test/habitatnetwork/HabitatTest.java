/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitatnetwork;

import java.awt.Graphics;
import java.awt.Image;
import java.io.PipedWriter;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author iUser
 */
public class HabitatTest {
    
    public HabitatTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of setPausedMoto method, of class Habitat.
     */
    @Test
    public void testSetPausedMoto() {
        System.out.println("setPausedMoto");
        boolean pausedMoto = false;
        Habitat instance = new Habitat();
        instance.setPausedMoto(pausedMoto);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPausedCar method, of class Habitat.
     */
    @Test
    public void testSetPausedCar() {
        System.out.println("setPausedCar");
        boolean pausedCar = false;
        Habitat instance = new Habitat();
        instance.setPausedCar(pausedCar);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPausedMoto method, of class Habitat.
     */
    @Test
    public void testIsPausedMoto() {
        System.out.println("isPausedMoto");
        Habitat instance = new Habitat();
        boolean expResult = false;
        boolean result = instance.isPausedMoto();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPausedCar method, of class Habitat.
     */
    @Test
    public void testIsPausedCar() {
        System.out.println("isPausedCar");
        Habitat instance = new Habitat();
        boolean expResult = false;
        boolean result = instance.isPausedCar();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEmul_progress method, of class Habitat.
     */
    @Test
    public void testIsEmul_progress() {
        System.out.println("isEmul_progress");
        Habitat instance = new Habitat();
        boolean expResult = false;
        boolean result = instance.isEmul_progress();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVel_count method, of class Habitat.
     */
    @Test
    public void testSetVel_count() {
        System.out.println("setVel_count");
        int vel_count = 0;
        Habitat instance = new Habitat();
        instance.setVel_count(vel_count);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVel_count method, of class Habitat.
     */
    @Test
    public void testGetVel_count() {
        System.out.println("getVel_count");
        Habitat instance = new Habitat();
        int expResult = 0;
        int result = instance.getVel_count();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCar_count method, of class Habitat.
     */
    @Test
    public void testGetCar_count() {
        System.out.println("getCar_count");
        Habitat instance = new Habitat();
        int expResult = 0;
        int result = instance.getCar_count();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCar_count method, of class Habitat.
     */
    @Test
    public void testSetCar_count() {
        System.out.println("setCar_count");
        int car_count = 0;
        Habitat instance = new Habitat();
        instance.setCar_count(car_count);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMoto_count method, of class Habitat.
     */
    @Test
    public void testSetMoto_count() {
        System.out.println("setMoto_count");
        int moto_count = 0;
        Habitat instance = new Habitat();
        instance.setMoto_count(moto_count);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMoto_count method, of class Habitat.
     */
    @Test
    public void testGetMoto_count() {
        System.out.println("getMoto_count");
        Habitat instance = new Habitat();
        int expResult = 0;
        int result = instance.getMoto_count();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMotoPriority method, of class Habitat.
     */
    @Test
    public void testSetMotoPriority() {
        System.out.println("setMotoPriority");
        int prior = 0;
        Habitat instance = new Habitat();
        instance.setMotoPriority(prior);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCarPriority method, of class Habitat.
     */
    @Test
    public void testSetCarPriority() {
        System.out.println("setCarPriority");
        int prior = 0;
        Habitat instance = new Habitat();
        instance.setCarPriority(prior);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_lst_copy method, of class Habitat.
     */
    @Test
    public void testGet_lst_copy() {
        System.out.println("get_lst_copy");
        Habitat instance = new Habitat();
        CopyOnWriteArrayList<BaseAI> expResult = null;
        CopyOnWriteArrayList<BaseAI> result = instance.get_lst_copy();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update_lst method, of class Habitat.
     */
    @Test
    public void testUpdate_lst() {
        System.out.println("update_lst");
        CopyOnWriteArrayList<BaseAI> income_lst = null;
        Habitat instance = new Habitat();
        instance.update_lst(income_lst);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPeriod method, of class Habitat.
     */
    @Test
    public void testGetPeriod() {
        System.out.println("getPeriod");
        Habitat instance = new Habitat();
        int expResult = 0;
        int result = instance.getPeriod();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setP_moto method, of class Habitat.
     */
    @Test
    public void testSetP_moto() {
        System.out.println("setP_moto");
        String s = "";
        Habitat instance = new Habitat();
        instance.setP_moto(s);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setP_car method, of class Habitat.
     */
    @Test
    public void testSetP_car() {
        System.out.println("setP_car");
        String s = "";
        Habitat instance = new Habitat();
        instance.setP_car(s);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatistic method, of class Habitat.
     */
    @Test
    public void testGetStatistic() {
        System.out.println("getStatistic");
        Habitat instance = new Habitat();
        String expResult = "";
        String result = instance.getStatistic();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of start_sim method, of class Habitat.
     */
    @Test
    public void testStart_sim() {
        System.out.println("start_sim");
        Habitat instance = new Habitat();
        instance.start_sim();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stop_sim method, of class Habitat.
     */
    @Test
    public void testStop_sim() {
        System.out.println("stop_sim");
        Habitat instance = new Habitat();
        instance.stop_sim();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pause_sim method, of class Habitat.
     */
    @Test
    public void testPause_sim() {
        System.out.println("pause_sim");
        Habitat instance = new Habitat();
        boolean expResult = false;
        boolean result = instance.pause_sim();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hold_sim method, of class Habitat.
     */
    @Test
    public void testHold_sim() {
        System.out.println("hold_sim");
        Habitat instance = new Habitat();
        instance.hold_sim();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unhold_sim method, of class Habitat.
     */
    @Test
    public void testUnhold_sim() {
        System.out.println("unhold_sim");
        Habitat instance = new Habitat();
        instance.unhold_sim();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of set_period method, of class Habitat.
     */
    @Test
    public void testSet_period() {
        System.out.println("set_period");
        int val = 0;
        Habitat instance = new Habitat();
        instance.set_period(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of trig_timer method, of class Habitat.
     */
    @Test
    public void testTrig_timer() {
        System.out.println("trig_timer");
        Habitat instance = new Habitat();
        boolean expResult = false;
        boolean result = instance.trig_timer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of set_timer_show method, of class Habitat.
     */
    @Test
    public void testSet_timer_show() {
        System.out.println("set_timer_show");
        boolean state = false;
        Habitat instance = new Habitat();
        instance.set_timer_show(state);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of timerValueState method, of class Habitat.
     */
    @Test
    public void testTimerValueState() {
        System.out.println("timerValueState");
        Habitat instance = new Habitat();
        boolean expResult = false;
        boolean result = instance.timerValueState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unfreezeMoto method, of class Habitat.
     */
    @Test
    public void testUnfreezeMoto() {
        System.out.println("unfreezeMoto");
        Habitat instance = new Habitat();
        instance.unfreezeMoto();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unfreezeCar method, of class Habitat.
     */
    @Test
    public void testUnfreezeCar() {
        System.out.println("unfreezeCar");
        Habitat instance = new Habitat();
        instance.unfreezeCar();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of imageUpdate method, of class Habitat.
     */
    @Test
    public void testImageUpdate() {
        System.out.println("imageUpdate");
        Image img = null;
        int infoflags = 0;
        int x = 0;
        int y = 0;
        int w = 0;
        int h = 0;
        Habitat instance = new Habitat();
        boolean expResult = false;
        boolean result = instance.imageUpdate(img, infoflags, x, y, w, h);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Update method, of class Habitat.
     */
    @Test
    public void testUpdate() {
        System.out.println("Update");
        double elapsedTime = 0.0;
        Habitat instance = new Habitat();
        instance.Update(elapsedTime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of paint method, of class Habitat.
     */
    @Test
    public void testPaint() {
        System.out.println("paint");
        Graphics g = null;
        Habitat instance = new Habitat();
        instance.paint(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWr_Stream method, of class Habitat.
     */
    @Test
    public void testGetWr_Stream() {
        System.out.println("getWr_Stream");
        Habitat instance = new Habitat();
        PipedWriter expResult = null;
        PipedWriter result = instance.getWr_Stream();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of write_to_console method, of class Habitat.
     */
    @Test
    public void testWrite_to_console() {
        System.out.println("write_to_console");
        String s = "";
        Habitat instance = new Habitat();
        instance.write_to_console(s);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ReduceMotoCountBy method, of class Habitat.
     */
    @Test
    public void testReduceMotoCountBy() {
        System.out.println("ReduceMotoCountBy");
        int val = 0;
        Habitat instance = new Habitat();
        instance.ReduceMotoCountBy(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
