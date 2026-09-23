import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotMachineC2Test.
 *
 * @author Santiago Vargas
 */
public class SlotMachineC2Test
{
    private SlotMachine machine;

    /**
     * Default constructor for test class SlotMachineC2Test
     */
    public SlotMachineC2Test()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        machine = new SlotMachine();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    /*
    @Test
    public void AAACreateBigMachine()
    {
        machine.makeVisible();
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addWheel(4);
        machine.addWheel(5);
        
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(1, "black");  
        machine.addSymbol(1, "yellow");
        machine.addSymbol(1, "magenta");
        machine.addSymbol(1, "green");
        
        machine.addSymbol(2, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(2, "black");
        machine.addSymbol(2, "yellow");
        machine.addSymbol(2, "magenta");
        machine.addSymbol(2, "green");
        
        machine.addSymbol(3, "red");
        machine.addSymbol(3, "blue");
        machine.addSymbol(3, "black");
        machine.addSymbol(3, "yellow");
        machine.addSymbol(3, "magenta");
        machine.addSymbol(3, "green");
        
        machine.addSymbol(4, "red");
        machine.addSymbol(4, "blue");
        machine.addSymbol(4, "black");
        machine.addSymbol(4, "yellow");
        machine.addSymbol(4, "magenta");
        machine.addSymbol(4, "green");
        
        machine.addSymbol(5, "red");
        machine.addSymbol(5, "blue");
        machine.addSymbol(5, "black");
        machine.addSymbol(5, "yellow");
        machine.addSymbol(5, "magenta");
        machine.addSymbol(5, "green");
        
        machine.spin(new String[]{"black", "black", "black", "black", "black"});
        assertTrue(machine.isJackpot());
    }
    //*/
   
    // ---------------------------------------------------------
    // addWheel / delWheel
    // ---------------------------------------------------------

    @Test
    public void shouldAddWheelToEmptyMachine()
    {
        machine.addWheel(1);

        assertEquals(0, machine.symbols().length);
        assertFalse(machine.isJackpot());
    }

    @Test
    public void shouldAddWheelInTheMiddle()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");
        
        assertArrayEquals(
            new String[]{"red", "red"},
            machine.symbols()
        );
        
        machine.addWheel(2);
        machine.addSymbol(2, "green");
    
        assertArrayEquals(
            new String[]{"red", "green", "red"},
            machine.symbols()
        );
    }

    @Test
    public void shouldNotAddWheelInInvalidPosition()
    {
        machine.addWheel(2);

        assertFalse(machine.ok());
        assertEquals(0, machine.symbols().length);
    }

    @Test
    public void shouldDeleteWheel()
    {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.delWheel(1);

        assertFalse(machine.isJackpot());
    }

    @Test
    public void shouldDeleteWheelInTheMiddle()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");

        machine.delWheel(2);

        assertArrayEquals(
            new String[]{"red", "green"},
            machine.symbols()
        );
    }

    @Test
    public void shouldNotDeleteWheelInInvalidPosition()
    {
        machine.addWheel(1);

        machine.delWheel(2);

        assertFalse(machine.ok());
    }


    // ---------------------------------------------------------
    // addSymbol / delSymbol
    // ---------------------------------------------------------

    @Test
    public void shouldAddSymbolToWheel()
    {
        machine.addWheel(1);

        machine.addSymbol(1, "red");

        assertArrayEquals(
            new String[]{"red"},
            machine.symbols()
        );
    }

    @Test
    public void shouldAddSeveralSymbolsToSameWheel()
    {
        machine.addWheel(1);

        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(1, "green");

        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.symbols()
        );
    }

    @Test
    public void shouldAddSymbolsToSeveralWheels()
    {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");

        assertArrayEquals(
            new String[]{"red", "blue"},
            machine.symbols()
        );
    }

    @Test
    public void shouldNotAddSymbolToInvalidWheel()
    {
        machine.addSymbol(1, "red");

        assertFalse(machine.ok());
        assertEquals(0, machine.symbols().length);
    }

    @Test
    public void shouldDeleteSymbolFromAllWheels()
    {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(2, "red");

        machine.delSymbol("red");

        assertArrayEquals(
            new String[]{"blue"},
            machine.symbols()
        );
    }

    @Test
    public void shouldDeleteCurrentSymbol()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.placeSymbol(1, "red");
        machine.delSymbol("red");

        assertEquals(0, machine.configuration().length);
    }
    
    @Test
    public void shouldNotBeOkAfterDeletingNonexistentSymbol()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
    
        machine.delSymbol("blue");
    
        assertFalse(machine.ok());
    }


    // ---------------------------------------------------------
    // placeSymbol
    // ---------------------------------------------------------

    @Test
    public void shouldPlaceExistingSymbol()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.placeSymbol(1, "blue");

        assertArrayEquals(
            new String[]{"blue"},
            machine.configuration()
        );

        assertTrue(machine.ok());
    }

    @Test
    public void shouldChangeCurrentSymbol()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(1, "blue");

        assertArrayEquals(
            new String[]{"blue"},
            machine.configuration()
        );
    }

    @Test
    public void shouldNotPlaceNonexistentSymbol()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.placeSymbol(1, "blue");

        assertFalse(machine.ok());
        assertEquals(0, machine.configuration().length);
    }
    
    @Test
    public void shouldNotPlaceSymbolInInvalidWheel()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
    
        machine.placeSymbol(2, "red");
    
        assertFalse(machine.ok());
    }


    // ---------------------------------------------------------
    // spin
    // ---------------------------------------------------------

    @Test
    public void shouldSpinWheelWithSymbols()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.spin(1);

        assertEquals(1, machine.configuration().length);

        String result = machine.configuration()[0];

        assertTrue(
            result.equals("red") || result.equals("blue")
        );
    }

    @Test
    public void shouldSpinAllWheels()
    {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.addSymbol(2, "green");
        machine.addSymbol(2, "yellow");

        machine.spin();

        assertEquals(2, machine.configuration().length);

        assertTrue(
            machine.configuration()[0].equals("red") ||
            machine.configuration()[0].equals("blue")
        );

        assertTrue(
            machine.configuration()[1].equals("green") ||
            machine.configuration()[1].equals("yellow")
        );
    }

    @Test
    public void shouldNotSpinWheelWithoutSymbols()
    {
        machine.addWheel(1);

        machine.spin(1);

        assertFalse(machine.ok());
        assertEquals(0, machine.configuration().length);
    }

    @Test
    public void shouldNotSpinAllWheelsIfOneHasNoSymbols()
    {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red");

        machine.spin();

        assertFalse(machine.ok());
    }
    
    @Test
    public void shouldNotSpinInvalidWheel()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
    
        machine.spin(2);
    
        assertFalse(machine.ok());
    }
    
    // Spin with steps ---------------------------------------------------------
    
    @Test
    public void shouldSpinWheelSeveralSteps()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(1, "green");
    
        machine.spin(1, 3);
    
        assertEquals(1, machine.configuration().length);
    }
    
    @Test
    public void shouldSpinWheelZeroSteps()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
    
        machine.placeSymbol(1, "red");
        machine.spin(1, 0);
    
        assertEquals("red", machine.configuration()[0]);
    }
    
    @Test
    public void shouldNotSpinNonexistentWheelWithSteps()
    {
        machine.spin(1, 3);
    
        assertEquals(0, machine.configuration().length);
    }
    
    // Spin with set of symbols ---------------------------------------------------------
    
    @Test
    public void shouldSpinToSetSymbols()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
    
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
    
        machine.addSymbol(2, "green");
        machine.addSymbol(2, "yellow");
    
        machine.addSymbol(3, "black");
        machine.addSymbol(3, "white");
    
        String[] setSymbols = {"blue", "yellow", "white"};
    
        machine.spin(setSymbols);
    
        assertEquals("blue", machine.configuration()[0]);
        assertEquals("yellow", machine.configuration()[1]);
        assertEquals("white", machine.configuration()[2]);
    }
    
    @Test
    public void shouldSpinToSetSymbolsWhenWheelsHaveNoActualSymbol()
    {
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
    
        machine.addSymbol(2, "green");
        machine.addSymbol(2, "yellow");
    
        String[] setSymbols = {"red", "yellow"};
    
        machine.spin(setSymbols);
    
        assertEquals("red", machine.configuration()[0]);
        assertEquals("yellow", machine.configuration()[1]);
    }
    
    // ---------------------------------------------------------
    // symbols
    // ---------------------------------------------------------

    @Test
    public void shouldReturnAllSymbolsInWheelOrder()
    {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "green");

        assertArrayEquals(
            new String[]{"red", "blue", "green"},
            machine.symbols()
        );
    }

    @Test
    public void shouldReturnEmptyArrayWhenThereAreNoSymbols()
    {
        assertEquals(0, machine.symbols().length);
    }


    // ---------------------------------------------------------
    // distinctSymbols
    // ---------------------------------------------------------

    @Test
    public void shouldCountDistinctSymbols()
    {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "red");

        assertEquals(2, machine.distinctSymbols());
    }

    @Test
    public void shouldReturnZeroDistinctSymbolsWhenThereAreNoSymbols()
    {
        assertEquals(0, machine.distinctSymbols());
    }


    // ---------------------------------------------------------
    // configuration
    // ---------------------------------------------------------

    @Test
    public void shouldReturnEmptyConfigurationInitially()
    {
        assertEquals(0, machine.configuration().length);
    }

    @Test
    public void shouldReturnCurrentSymbolsInWheelOrder()
    {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        assertArrayEquals(
            new String[]{"red", "blue"},
            machine.configuration()
        );
    }

    @Test
    public void shouldReturnOnlyWheelsWithCurrentSymbols()
    {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");

        machine.placeSymbol(1, "red");

        assertArrayEquals(
            new String[]{"red"},
            machine.configuration()
        );
    }


    // ---------------------------------------------------------
    // isJackpot
    // ---------------------------------------------------------

    @Test
    public void shouldNotBeJackpotWithNoWheels()
    {
        assertFalse(machine.isJackpot());
    }

    @Test
    public void shouldBeJackpotWithOneWheelAndOneCurrentSymbol()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");

        assertTrue(machine.isJackpot());
    }

    @Test
    public void shouldNotBeJackpotWhenAWheelHasNoCurrentSymbol()
    {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");

        assertFalse(machine.isJackpot());
    }

    @Test
    public void shouldBeJackpotWhenAllCurrentSymbolsHaveSameColor()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");
        machine.addSymbol(3, "red");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");
        machine.placeSymbol(3, "red");

        assertTrue(machine.isJackpot());
    }

    @Test
    public void shouldNotBeJackpotWhenCurrentSymbolsHaveDifferentColors()
    {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        assertFalse(machine.isJackpot());
    }
    
    @Test
    public void shouldNotBeJackpotAfterDeletingCurrentSymbol()
    {
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");
    
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");
    
        assertTrue(machine.isJackpot());
    
        machine.delSymbol("red");
    
        assertFalse(machine.isJackpot());
    }

    // ---------------------------------------------------------
    // ok
    // ---------------------------------------------------------

    @Test
    public void shouldInitiallyBeOk()
    {
        assertTrue(machine.ok());
    }

    @Test
    public void shouldBeOkAfterSuccessfulPlaceSymbol()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.placeSymbol(1, "red");

        assertTrue(machine.ok());
    }

    @Test
    public void shouldNotBeOkAfterFailedPlaceSymbol()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.placeSymbol(1, "blue");

        assertFalse(machine.ok());
    }

    @Test
    public void shouldNotBeOkAfterInvalidWheelPosition()
    {
        machine.addWheel(1);

        machine.delWheel(2);

        assertFalse(machine.ok());
    }

    // ---------------------------------------------------------
    // operationOK
    // ---------------------------------------------------------
    @Test
    public void shouldBeOkAfterSuccessfulOperationFollowingFailure()
    {
        machine.addWheel(1);
    
        machine.placeSymbol(1, "red");
        assertFalse(machine.ok());
    
        machine.addSymbol(1, "red");
    
        assertTrue(machine.ok());
    }
    
    // ---------------------------------------------------------
    // Invisible operation
    // ---------------------------------------------------------
    @Test
    public void shouldWorkWithoutBeingVisible()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");

        assertArrayEquals(
            new String[]{"red"},
            machine.configuration()
        );

        assertTrue(machine.isJackpot());
    }

    @Test
    public void shouldSpinWithoutBeingVisible()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.spin(1);

        assertEquals(1, machine.configuration().length);
    }
    
    @Test
    public void shouldMakeMachineInvisibleAfterSeveralOperations()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
    
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(1, "green");
    
        machine.addSymbol(2, "yellow");
        machine.addSymbol(2, "black");
        machine.addSymbol(2, "white");
    
        machine.addSymbol(3, "magenta");
        machine.addSymbol(3, "red");
        machine.addSymbol(3, "blue");
    
        machine.makeVisible();
        Canvas.getCanvas().wait(1000);
    
        machine.spin(1, 3);
        Canvas.getCanvas().wait(500);
    
        machine.spin(2, 4);
        Canvas.getCanvas().wait(500);
    
        machine.spin(3, 5);
        Canvas.getCanvas().wait(500);
    
        machine.swap(1, 3);
        Canvas.getCanvas().wait(500);
    
        machine.spin(1);
        machine.spin(2);
        machine.spin(3);
        Canvas.getCanvas().wait(500);
    
        machine.lock(1);
        machine.unlock(1);
    
        machine.swap(1, 2);
        Canvas.getCanvas().wait(500);
    
        machine.spin(new String[]{"yellow", "red", "green"});
        Canvas.getCanvas().wait(1000);
    
        machine.makeInvisible();
    }
    
    // ---------------------------------------------------------
    // Lock / Unlock
    // ---------------------------------------------------------
    
    @Test
    public void shouldLockWheel()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
    
        machine.lock(1);
    
        machine.placeSymbol(1, "red");
    
        assertEquals(0, machine.configuration().length);
    }
    
    @Test
    public void shouldUnlockWheel()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
    
        machine.lock(1);
        machine.unlock(1);
    
        machine.placeSymbol(1, "red");
    
        assertEquals(1, machine.configuration().length);
    }
    
    @Test
    public void shouldNotLockNonexistentWheel()
    {
        machine.lock(1);
    
        assertEquals(0, machine.configuration().length);
    }
    
    @Test
    public void shouldNotUnlockNonexistentWheel()
    {
        machine.unlock(1);
    
        assertEquals(0, machine.configuration().length);
    }
    
    
    // ---------------------------------------------------------
    // Swap
    // ---------------------------------------------------------
    
    @Test
    public void shouldSwapWheels()
    {
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
    
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
    
        machine.swap(1, 2);
    
        assertEquals("blue", machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
    }
    
    @Test
    public void shouldNotSwapSameWheel()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");
    
        machine.swap(1, 1);
    
        assertEquals("red", machine.configuration()[0]);
    }
    
    @Test
    public void shouldNotSwapWithNonexistentFirstWheel()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");
    
        machine.swap(0, 1);
    
        assertEquals("red", machine.configuration()[0]);
    }
    
    @Test
    public void shouldNotSwapWithNonexistentSecondWheel()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");
    
        machine.swap(1, 2);
    
        assertEquals("red", machine.configuration()[0]);
    }
    
    @Test
    public void shouldSpinSwappedWheelsWithTheirOwnSymbols()
    {
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
    
        machine.addSymbol(2, "green");
        machine.addSymbol(2, "yellow");
    
        machine.swap(1, 2);
    
        machine.spin(new String[]{"green", "red"});
    
        assertEquals("green", machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
    }
}