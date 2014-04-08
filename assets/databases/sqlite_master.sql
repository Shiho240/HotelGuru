BEGIN TRANSACTION;
INSERT INTO "sqlite_master" VALUES('table','User','User',2,'CREATE TABLE User (uid INTEGER PRIMARY KEY, Username TEXT, Password TEXT)');
INSERT INTO "sqlite_master" VALUES('table','Ships','Ships',4,'CREATE TABLE Ships (sid INTEGER PRIMARY KEY, Ship_Name TEXT, Ship_Year NUMERIC, Cruise_Line TEXT, Capacity NUMERIC, Ship_Decks NUMERIC, Ship_Registry TEXT, Ship_Speed NUMERIC, Ship_Length NUMERIC)');
INSERT INTO "sqlite_master" VALUES('table','Rooms','Rooms',3,'CREATE TABLE Rooms (rid INTEGER PRIMARY KEY, Room_Num NUMERIC, Room_Type TEXT, Room_Desc TEXT, Room_Rating NUMERIC, Room_Deck NUMERIC, Ship_Name TEXT, Cruise_Line TEXT, Room_Interior TEXT, Room_Exterior TEXT, ButtonX INTEGER, ButtonY INTEGER, Locale TEXT, Occupancy INT, Special TEXT, Room_Size INT, Balcony_Size INT)');
COMMIT;
