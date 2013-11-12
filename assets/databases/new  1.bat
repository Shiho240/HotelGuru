for // %%i in (12002, 2, 12010) do
	%%j = ((%%i - 12000)*15)+140
	sqlite3 HotelGuru "INSERT INTO Rooms (Room_Num, Room_Type, Room_Desc, Room_Rating, Room_Deck, Ship_Name, Cruise_Line, Room_Interior, Room_Exterior, ButtonX, ButtonY) VALUES (%%i, "Forward Brittania Balcony Stateroom, "At any time of the day your luxury stateroom provides a welcome enclave of comfort and good taste. Sit and watch the world go by from your own private balcony.Your Stateroom Includes:
24-hour room service
Bathrobe and slippers
Nightly turndown service with pillow chocolate
Satellite TV with multi-language film and music channels
Refrigerator, safe, hair dryer
Daily shipboard newspaper
Half a bottle of Bon Voyage wine
Direct-dial telephone
Fruit basket (on request)
220V British 3-pin and 110V 2-pin sockets", 4, 12, "Queen Mary 2", "Cunard", "www.google.com", "www.google.com", 460,%%j  );"