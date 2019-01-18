import pymysql

#
def showTuple(cur):
    count=0
    desc = [x[0] for x in cur.description]
    print(*desc, sep=",  ")
    for row in cur.fetchall():
        for i in row :
            if i==row[0] and len(row)==1:
                print("[",i,end = " ]")
            elif i==row[0]:
                print("[",i,end = ",  ")   
            elif i!=row[-1]:
                print (i, end = ",  ")
            else:
                print (i, end = " ]")
        print()
        count +=1
        if count%30==0:
            reply = input ( "....more ? (y/n)")
            if reply != "y" :
                break
            else:
                print ()
    return
#

while True:
    try:
        dbname = "airport"
        username=input("Type your username:")
        password=input("Type your password:")
        con = pymysql.connect(host = "localhost",\
             user=username, passwd=password, db=dbname, charset="utf8")
    except pymysql.Error as e:
        print ("An error occurred:", e)
    else:
        con.isolation_level = None
        cur = con.cursor()
        cur.execute("select version()")
        #print("Version of my DataBase: {}".format(cur.fetchone()))
        break
print("Welcome!") 
print("Type 'info' for information about the use of the program.")
while True:
    myinput = input(">>>")
    ###  
    if myinput == "info":
        print("Type '1' to see all the airplanes.")
        print("Type '2' to see all the available airplanes in the Parking Area.")
        print("Type '3' to see all the departures.")
        print("Type '4' to see all the arrivals.")
        print("Type '5' to see all the DELAYS on departures.")
        print("Type '6' to see all the DELAYS on arrivals.")
        print("Type '7' to see the occupancy of a certain flight.")
        print("Type 'departure' to insert a new departure.")
        print("Type 'arrival' to insert a new arrival.")
        print("Type 'select' to make a specific SELECT execution.")
        print("Type 'exit' to exit from the program.")
    ###
    elif myinput == "1":
        print ("Airplanes :")
        cur.execute("SELECT * FROM AIRPLANE;")
        showTuple(cur)
    ###
    elif myinput == "2":
        print ("Available airplanes :")
        cur.execute("SELECT AIRPLANE.ID_airplane,AIRPLANE.no_of_seats,AIRPLANE.airline,IS_PLACED_IN.ID_of_airplane_area\
        FROM AIRPLANE JOIN IS_PLACED_IN\
        ON\
        AIRPLANE.ID_airplane=IS_PLACED_IN.ID_of_placed_airplane;")
        showTuple(cur)
    ###
    elif myinput == "3":
        print ("Departures :")
        cur.execute("SELECT * FROM DEPARTURE ;")
        showTuple(cur)
    ###
    elif myinput == "4":
        print ("Arrivals :")
        cur.execute("SELECT * FROM ARRIVAL ;")
        showTuple(cur)
    ###
    elif myinput == "5":
        print ("DELAYS on departures :")
        cur.execute("SELECT DEPARTURE.ID_of_departure_flight,ROUTE.ID_airplane,\
        DEPARTURE.scheduled_departure_time,DEPARTURE.actual_departure_time,\
        DEPARTURE.ID_gate,DEPARTURE.ID_corridor,DEPARTURE.ICAO_code,DEPARTURE.IATA_code\
        FROM (DEPARTURE JOIN ROUTE ON \
        DEPARTURE.ID_of_departure_flight = ROUTE.ID_flight) \
        WHERE DEPARTURE.actual_departure_time IS NULL\
        AND DEPARTURE.scheduled_departure_time<CURRENT_TIMESTAMP ;")
        showTuple(cur)
    ###
    elif myinput == "6":
        print ("DELAYS on arrivals :")
        cur.execute("SELECT ARRIVAL.ID_of_arriving_flight, ROUTE.ID_airplane, \
        ARRIVAL.scheduled_arrival_time,ARRIVAL.actual_arrival_time,\
        ARRIVAL.ID_gate,ARRIVAL.ID_corridor,ARRIVAL.ICAO_code,ARRIVAL.IATA_code\
        FROM (ARRIVAL JOIN ROUTE ON \
        ARRIVAL.ID_of_arriving_flight = ROUTE.ID_flight) \
        WHERE ARRIVAL.actual_arrival_time IS NULL\
        AND ARRIVAL.scheduled_arrival_time<CURRENT_TIMESTAMP ;")
        showTuple(cur)
    ###
    elif myinput == "7":
        idflight = input("Type the flight ID for search:")
        try:
            cur.execute("SELECT * FROM ROUTE WHERE ID_flight='"+idflight+"';")
            result = cur.fetchone()
            if result==None:
                print("The ID of flight submitted does not exist.")
                continue
            cur.execute("SELECT AIRPLANE.no_of_seats, \
            ROUTE.no_of_passengers FROM (AIRPLANE JOIN ROUTE ON \
            AIRPLANE.ID_airplane=ROUTE.ID_airplane)\
            WHERE ROUTE.ID_flight='"+idflight+"';")
            result = cur.fetchone()
            seats=int(result[0])
            passengers=int(result[1])
            print("No of passengers: "+str(passengers)+".")
            print("Available seats: "+str(seats-passengers)+".")
            print("Occupancy: "+str(int(passengers)/int(seats)*100)+"%.")
        except pymysql.Error as e:
            print ("An error occurred:", e)      
    ###        
    elif myinput == "departure":
        print ("Add new departure:")
        idflight = input("Type the new flight ID:")
        idairplane = input("Type the airplane ID that will make the flight:")
        timeofflight = input("Type total time of next flight:")
        noofpassengers = input("Type number of passengers:")
        departuretime = input("Type scheduled departure time:")
        departurecorridor = input("Type the corridor ID of departure:")
        idgate = input("Type the gate ID of departure:")
        allowedtraversaltime = input("Type the allowed time for traversal to airplane:")
        icaocode = input("Type the ICAO code of next airport:")
        iatacode = input("Type the IATA code of next airport:")
        try:
            #
            cur.execute("SELECT * FROM ROUTE WHERE ID_flight='"+idflight+"';")
            result = cur.fetchone()
            if result!=None:
                print("The ID of flight submitted is already used.")
                continue
            cur.execute("SELECT * FROM AIRPLANE WHERE ID_airplane='"+idairplane+"';")
            result = cur.fetchone()
            if result==None:
                print("The ID of airplane submitted does not exist.")
                continue
            cur.execute("SELECT no_of_seats FROM AIRPLANE WHERE AIRPLANE.ID_airplane ='"+idairplane+"';")
            result = cur.fetchone()
            if  int(result[0])<int(noofpassengers):
                print("The no of passengers submitted exceed the no of max available seats.")
                continue
            cur.execute("SELECT * FROM CORRIDOR WHERE ID_corridor='"+departurecorridor+"';")
            result = cur.fetchone()
            if result==None:
                print("The ID of corridor submitted does not exist.")
                continue
            cur.execute("SELECT * FROM GATE WHERE ID_gate='"+idgate+"';")
            result = cur.fetchone()
            if result==None:
                print("The ID of gate submitted does not exist.")
                continue
            cur.execute("SELECT * FROM AIRPORT WHERE ICAO_code='"+icaocode+"' AND IATA_code='"+iatacode+"';")
            result = cur.fetchone()
            if result==None:
                print("The ICAO/IATA codes submitted do not exist.")
                continue
            #
            cur.execute("INSERT INTO ROUTE (`ID_flight`,`ID_airplane`,no_of_passengers) \
                    VALUES ('"+idflight+"','"+idairplane+"',"+noofpassengers+"); ")
            cur.execute('commit')
            cur.execute("INSERT INTO DEPARTURE (`ID_of_departure_flight`, `ID_gate`, `scheduled_departure_time`,`actual_departure_time`,`allowed_traversal_time`,`scheduled_total_time_of_next_flight`,`ICAO_code`, `IATA_code`, `ID_corridor`) \
                    VALUES ('"+idflight+"','"+idgate+"','"+departuretime+"',NULL,'"+allowedtraversaltime+"','"+timeofflight+"','"+icaocode+"','"+iatacode+"','"+departurecorridor+"'); ")
            cur.execute('commit')
            print ("Departure added successfully.")
        except pymysql.Error as e:
            print ("An error occurred:", e)  
    
    ###
    elif myinput == "arrival":
        print ("Add new arrival:")
        idflight = input("Type the new flight ID:")
        idairplane = input("Type the airplane ID that will make the arrival:")
        noofpassengers = input("Type number of passengers:")
        timeofflight = input("Type total time of last flight:")
        arrivaltime = input("Type scheduled arrival time:")
        arrivalcorridor = input("Type the corridor ID of arrival:")
        idgate = input("Type the gate ID of arrival:")
        allowedtraversaltime = input("Type the allowed time for traversal from airplane:")
        icaocode = input("Type the ICAO code of previous airport:")
        iatacode = input("Type the IATA code of previous airport:")
        try:
            #
            cur.execute("SELECT * FROM ROUTE WHERE ID_flight='"+idflight+"';")
            result = cur.fetchone()
            if result!=None:
                print (result)
                print("The ID of flight submitted is already used.")
                continue
            cur.execute("SELECT * FROM AIRPLANE WHERE ID_airplane='"+idairplane+"';")
            result = cur.fetchone()
            if result==None:
                print("The ID of airplane submitted does not exist.")
                continue
            cur.execute("SELECT no_of_seats FROM AIRPLANE WHERE AIRPLANE.ID_airplane ='"+idairplane+"';")
            result = cur.fetchone()
            if  int(result[0])<int(noofpassengers):
                print("The no of passengers submitted exceed the no of max available seats.")
                continue
            cur.execute("SELECT * FROM CORRIDOR WHERE ID_corridor='"+arrivalcorridor+"';")
            result = cur.fetchone()
            if result==None:
                print("The ID of corridor submitted does not exist.")
                continue
            cur.execute("SELECT * FROM GATE WHERE ID_gate='"+idgate+"';")
            result = cur.fetchone()
            if result==None:
                print("The ID of gate submitted does not exist.")
                continue
            cur.execute("SELECT * FROM AIRPORT WHERE ICAO_code='"+icaocode+"' AND IATA_code='"+iatacode+"';")
            result = cur.fetchone()
            if result==None:
                print("The ICAO/IATA codes submitted do not exist.")
                continue
            #
            cur.execute("INSERT INTO ROUTE (`ID_flight`,`ID_airplane`,no_of_passengers) \
                    VALUES ('"+idflight+"','"+idairplane+"',"+noofpassengers+"); ")
            cur.execute('commit')

            cur.execute("INSERT INTO ARRIVAL (`ID_of_arriving_flight`,`ID_gate`,`scheduled_arrival_time`,`actual_arrival_time`,`allowed_traversal_time`,`scheduled_total_time_of_last_flight`,`ICAO_code`, `IATA_code`, `ID_corridor`) \
                    VALUES ('"+idflight+"','"+idgate+"','"+arrivaltime+"',NULL,'"+allowedtraversaltime+"','"+timeofflight+"','"+icaocode+"','"+iatacode+"','"+arrivalcorridor+"'); ")
            cur.execute('commit')
            print ("Arrival added successfully.")
        except pymysql.Error as e:
            print ("An error occurred:", e)    
    ###
    elif myinput == "select":
        print ("Type your SELECT execution:")
        buffer = ""
        line = input('>>>')
        buffer += line
        print (buffer)
        if True: 
            try:
                buffer = buffer.strip()
                if buffer.lstrip().upper().startswith("SELECT"):
                    count=0
                    cur.execute(buffer)
                    showTuple(cur)
            except pymysql.Error as e:
                print ("An error occurred:", e)
            buffer = ""
    ###               
    elif myinput == "exit":
        print ("Bye!")     
        con.close()
        break
    ###
    else:
        print ("Error on typing.Type again(Type 'info' for details).")

        
