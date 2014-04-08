import-module SQLite
 mount-sqlite -name mydb -dataSource HotelGuru
 for($i=12002;$i -le 12010;$i+=2)
 {
 	$j = ((($i-12000)*15)+140)
 	new-item mydb:/Rooms -Room_Num $i -Room_Type "Forward Brittania Balcony Stateroom" -Room_Desc "At any time of the day your luxury stateroom provides a welcome enclave of comfort and good taste. Sit and watch the world go by from your own private balcony.Your Stateroom Includes: 24-hour room service,Bathrobe and slippers,Nightly turndown service with pillow chocolate,Satellite TV with multi-language film and music channels,Refrigerator, safe, hair dryer,Daily shipboard newspaper,Half a bottle of Bon Voyage wine,Direct-dial telephone,Fruit basket on request,220V British 3-pin and 110V 2-pin sockets" -Room_Rating 4 -Room_Deck 12 -Ship_Name "Queen Mary 2" -Cruise_Line "Cunard" -Room_Interior "www.google.com" -Room_Exterior "www.google.com" -ButtonX 460 -ButtonY $j
 }