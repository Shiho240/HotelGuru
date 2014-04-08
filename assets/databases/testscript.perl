#!/usr/bin/perl

use strict;
use DBI;

my $dbh = DBI->connect(          
    "dbi:SQLite:dbname=HotelGuru_Prod.sqlite", 
    "",                          
    "",                          
    { RaiseError => 1 },         
) or die $DBI::errstr;

for(my $i=12012; $i<=12028;$i+=2)
{
my $j = ((($i-12012)*7.5)+215);
$dbh->do("INSERT INTO Rooms(Room_Num, Room_Type, Room_Desc, Room_Rating, Room_Deck, Ship_Name, Cruise_Line,Room_Interior, Room_Exterior, ButtonX, ButtonY, Locale, Occupancy, Special, Room_Size, Balcony_Size) VALUES ('$i','Forward Brittania Club Balcony','Forward Stateroom with Balcony and Concierge service','0','12','Queen Mary 2','Cunard','www.google.com','www.google.com','460','$j','EN_US','2','None','248','81')");
}

my $id = $dbh->last_insert_id("", "", "Friends", "");
print "The last Id of the inserted row is $id\n";

$dbh->disconnect();