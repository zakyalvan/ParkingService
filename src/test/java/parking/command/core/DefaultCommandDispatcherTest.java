package parking.command.core;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import parking.command.impl.StatusInquiryCommandDispatchTest;
import parking.space.CurrentSpaceHolder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author zakyalvan
 */
public class DefaultCommandDispatcherTest {
    private CommandDispatcher dispatcher;

    @BeforeClass
    public void createDispatcher() {
        this.dispatcher = CommandDispatcher.defaultInstance();
    }

    @Test
    public void givenDefaultDispatcher_whenDispatchValidInputCommands_thenMustSucces() {
        CurrentSpaceHolder spaceHolder = CurrentSpaceHolder.instance();

        Integer spaceCapacity = 10;

        String createOutput = dispatcher.dispatch("create_parking_lot  " + spaceCapacity);
        assertThat(createOutput, is(String.format("Created a parking lot with %d slots", spaceCapacity)));
        assertThat(spaceHolder.get().capacity(), is(spaceCapacity));
        assertThat(spaceHolder.get().slotRegistry().available().size(), is(10));
        assertThat(spaceHolder.get().slotRegistry().occupied().size(), is(0));

        String parkOutput = dispatcher.dispatch("park  ASD-123-342-QWE  White");
        assertThat(parkOutput, is("Allocated slot number: 1"));
        assertThat(spaceHolder.get().slotRegistry().available().size(), is(9));
        assertThat(spaceHolder.get().slotRegistry().occupied().size(), is(1));

        String anotherParkOutput = dispatcher.dispatch("park ASD-123-342-QWS   Red");
        assertThat(anotherParkOutput, is("Allocated slot number: 2"));
        assertThat(spaceHolder.get().slotRegistry().available().size(), is(8));
        assertThat(spaceHolder.get().slotRegistry().occupied().size(), is(2));

        String dispatchOutput = dispatcher.dispatch(" Status  ");
        String expectedOutput = StatusInquiryCommandDispatchTest.CurrentOccupiedFormatHelper.format();
        assertThat(dispatchOutput, is(expectedOutput));

        String registerNumbers = dispatcher.dispatch("registration_numbers_for_cars_with_colour  White ");
        assertThat(registerNumbers, is("ASD-123-342-QWE"));

        String slotIndexesForWhite = dispatcher.dispatch("slot_numbers_for_cars_with_colour White");
        assertThat(slotIndexesForWhite, is("1"));


        String firstslotIndex = dispatcher.dispatch("slot_number_for_registration_number ASD-123-342-QWE");
        assertThat(firstslotIndex, is("1"));

        String secondSlotIndex = dispatcher.dispatch("slot_number_for_registration_number ACD-123-235-WWC");
        assertThat(secondSlotIndex, is("Not found"));

        String firstLeave = dispatcher.dispatch("leave 2");
        assertThat(firstLeave, is("Slot number 2 is free"));
        assertThat(spaceHolder.get().slotRegistry().available().size(), is(9));
        assertThat(spaceHolder.get().slotRegistry().occupied().size(), is(1));


        String secondLeave = dispatcher.dispatch("Leave 1");
        assertThat(secondLeave, is("Slot number 1 is free"));
        assertThat(spaceHolder.get().slotRegistry().available().size(), is(10));
        assertThat(spaceHolder.get().slotRegistry().occupied().size(), is(0));
    }
}
