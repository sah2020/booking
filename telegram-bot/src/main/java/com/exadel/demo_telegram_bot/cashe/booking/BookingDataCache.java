package com.exadel.demo_telegram_bot.cashe.booking;

import com.exadel.demo_telegram_bot.dto.BookingResTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class BookingDataCache {
    private final HashMap<String, BookingData> bookingDataHashMap = new HashMap<>();
    private final HashMap<String, BookingResTO> confirmationBookingHashMap = new HashMap<>();

    public BookingResTO getConfirmationBookingDto(String chatId){
        return confirmationBookingHashMap.get(chatId);
    }

    public void setConfirmationBooking(String chatId, BookingResTO bookingResTO){
        confirmationBookingHashMap.put(chatId, bookingResTO);
    }

    public BookingData getBookingData(String chatId){
        return bookingDataHashMap.getOrDefault(chatId, new BookingData());
    }

    public void setBookingData(String chatId, BookingData bookingData){
        bookingDataHashMap.put(chatId, bookingData);
    }

    public void setBookingDataBookingId(String chatId, String bookingId){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setBookingId(bookingId);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataIsAnyWorkplace(String chatId, boolean bool){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setAnyWorkplace(bool);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataIsParkingNeeded(String chatId, boolean bool){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setParkingNeeded(bool);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataCity(String chatId, String city){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setCity(city);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataOffice(String chatId, String officeName, String officeId){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setOfficeName(officeName);
        bookingData.setOfficeId(officeId);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataBookingType(String chatId, String bookingType){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setBookingType(bookingType);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataStartDate(String chatId, Date startDate){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setStartDate(startDate);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataEndDate(String chatId, Date endDate){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setEndDate(endDate);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataMap(String chatId, String mapNumber, String mapId){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setMapNumber(mapNumber);
        bookingData.setMapId(mapId);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataWorkplace(String chatId, String workplaceNumber, String workplaceId){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setWorkplaceNumber(workplaceNumber);
        bookingData.setWorkplaceId(workplaceId);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataExactFloor(String chatId, Boolean bool){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setExactFloor(bool);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataFloorKitchen(String chatId, Boolean bool){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setFloorKitchen(bool);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataKitchenYes(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getFloorKitchen()==null){
            setBookingDataFloorKitchen(chatId,true);
        }
        else if (bookingData.getFloorKitchen()){
            setBookingDataFloorKitchen(chatId, null);
        }
        else {
            setBookingDataFloorKitchen(chatId,true);
        }
    }

    public void setBookingDataKitchenNo(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getFloorKitchen()==null){
            setBookingDataFloorKitchen(chatId,false);
        }
        else if (!bookingData.getFloorKitchen()){
            setBookingDataFloorKitchen(chatId, null);
        }
        else {
            setBookingDataFloorKitchen(chatId,false);
        }
    }

    public void setBookingDataNextToWindow(String chatId, Boolean bool){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setNextToWindow(bool);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataNextToWindowYes(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getNextToWindow()==null){
            setBookingDataNextToWindow(chatId,true);
        }
        else if (bookingData.getNextToWindow()){
            setBookingDataNextToWindow(chatId, null);
        }
        else {
            setBookingDataNextToWindow(chatId,true);
        }
    }

    public void setBookingDataNextToWindowsNo(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getNextToWindow()==null){
            setBookingDataNextToWindow(chatId,false);
        }
        else if (!bookingData.getNextToWindow()){
            setBookingDataNextToWindow(chatId, null);
        }
        else {
            setBookingDataNextToWindow(chatId,false);
        }
    }


    public void setBookingDataHasPC(String chatId, Boolean bool){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setHasPC(bool);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataHasPCYes(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getHasPC()==null){
            setBookingDataHasPC(chatId,true);
        }
        else if (bookingData.getHasPC()){
            setBookingDataHasPC(chatId, null);
        }
        else {
            setBookingDataHasPC(chatId,true);
        }
    }

    public void setBookingDataHasPCNo(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getHasPC()==null){
            setBookingDataHasPC(chatId,false);
        }
        else if (!bookingData.getHasPC()){
            setBookingDataHasPC(chatId, null);
        }
        else {
            setBookingDataHasPC(chatId,false);
        }
    }

    public void setBookingDataHasMonitor(String chatId, Boolean bool){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setHasMonitor(bool);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataHasMonitorYes(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getHasMonitor()==null){
            setBookingDataHasMonitor(chatId,true);
        }
        else if (bookingData.getHasMonitor()){
            setBookingDataHasMonitor(chatId, null);
        }
        else {
            setBookingDataHasMonitor(chatId,true);
        }
    }

    public void setBookingDataHasMonitorNo(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getHasMonitor()==null){
            setBookingDataHasMonitor(chatId,false);
        }
        else if (!bookingData.getHasMonitor()){
            setBookingDataHasMonitor(chatId, null);
        }
        else {
            setBookingDataHasMonitor(chatId,false);
        }
    }

    public void setBookingDataHasKeyboard(String chatId, Boolean bool){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setHasKeyboard(bool);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataHasKeyboardYes(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getHasKeyboard()==null){
            setBookingDataHasKeyboard(chatId,true);
        }
        else if (bookingData.getHasKeyboard()){
            setBookingDataHasKeyboard(chatId, null);
        }
        else {
            setBookingDataHasKeyboard(chatId,true);
        }
    }

    public void setBookingDataHasKeyboardNo(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getHasKeyboard()==null){
            setBookingDataHasKeyboard(chatId,false);
        }
        else if (!bookingData.getHasKeyboard()){
            setBookingDataHasKeyboard(chatId, null);
        }
        else {
            setBookingDataHasKeyboard(chatId,false);
        }
    }


    public void setBookingDataHasMouse(String chatId, Boolean bool){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setHasMouse(bool);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataHasMouseYes(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getHasMouse()==null){
            setBookingDataHasMouse(chatId,true);
        }
        else if (bookingData.getHasMouse()){
            setBookingDataHasMouse(chatId, null);
        }
        else {
            setBookingDataHasMouse(chatId,true);
        }
    }

    public void setBookingDataHasMouseNo(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getHasMouse()==null){
            setBookingDataHasMouse(chatId,false);
        }
        else if (!bookingData.getHasMouse()){
            setBookingDataHasMouse(chatId, null);
        }
        else {
            setBookingDataHasMouse(chatId,false);
        }
    }


    public void setBookingDataHasHeadset(String chatId, Boolean bool){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setHasHeadset(bool);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataHasHeadsetYes(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getHasHeadset()==null){
            setBookingDataHasHeadset(chatId,true);
        }
        else if (bookingData.getHasHeadset()){
            setBookingDataHasHeadset(chatId, null);
        }
        else {
            setBookingDataHasHeadset(chatId,true);
        }
    }

    public void setBookingDataHasHeadsetNo(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getHasHeadset()==null){
            setBookingDataHasHeadset(chatId,false);
        }
        else if (!bookingData.getHasHeadset()){
            setBookingDataHasHeadset(chatId, null);
        }
        else {
            setBookingDataHasHeadset(chatId,false);
        }
    }


    public void setBookingDataFloorMeeting(String chatId, Boolean bool){
        BookingData bookingData = getBookingData(chatId);
        bookingData.setFloorMeetingRoom(bool);
        setBookingData(chatId,bookingData);
    }

    public void setBookingDataConfRoomYes(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getFloorMeetingRoom()==null){
            setBookingDataFloorMeeting(chatId,true);
        }
        else if (bookingData.getFloorMeetingRoom()){
            setBookingDataFloorMeeting(chatId, null);
        }
        else {
            setBookingDataFloorMeeting(chatId,true);
        }
    }

    public void setBookingDataConfRoomsNo(String chatId){
        final BookingData bookingData = getBookingData(chatId);
        if (bookingData.getFloorMeetingRoom()==null){
            setBookingDataFloorMeeting(chatId,false);
        }
        else if (!bookingData.getFloorMeetingRoom()){
            setBookingDataFloorMeeting(chatId, null);
        }
        else {
            setBookingDataFloorMeeting(chatId,false);
        }
    }

    public void setBookingDataIsWorkplaceSelected(String chatId, boolean bool){
        final BookingData bookingData = getBookingData(chatId);
        bookingData.setWorkplaceSelected(bool);
        setBookingData(chatId, bookingData);
    }

    public void editBookingDataAtAskBookingType(String chatId){
        BookingData bookingData = getBookingData(chatId);
        BookingData editedBookingData = new BookingData();

        editedBookingData.setCity(bookingData.getCity());
        editedBookingData.setOfficeId(bookingData.getOfficeId());
        editedBookingData.setOfficeName(bookingData.getOfficeName());

        setBookingData(chatId,editedBookingData);
    }

    public void editBookingDataAtAskFirstDay(String chatId){
        BookingData bookingData = getBookingData(chatId);
        BookingData editedBookingData = new BookingData();

        editedBookingData.setCity(bookingData.getCity());
        editedBookingData.setOfficeId(bookingData.getOfficeId());
        editedBookingData.setOfficeName(bookingData.getOfficeName());
        editedBookingData.setBookingType(bookingData.getBookingType());

        setBookingData(chatId,editedBookingData);
    }

    public void editBookingDataAtAskLastDay(String chatId){
        BookingData bookingData = getBookingData(chatId);
        BookingData editedBookingData = new BookingData();

        editedBookingData.setCity(bookingData.getCity());
        editedBookingData.setOfficeId(bookingData.getOfficeId());
        editedBookingData.setOfficeName(bookingData.getOfficeName());
        editedBookingData.setBookingType(bookingData.getBookingType());
        editedBookingData.setStartDate(bookingData.getStartDate());

        setBookingData(chatId,editedBookingData);
    }
}
