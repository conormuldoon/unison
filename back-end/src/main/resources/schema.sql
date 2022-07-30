    
    create table if not exists Location (
       name varchar(255) not null,
        geom GEOMETRY,
        user_userName varchar(255),
        primary key (name)
    );
    
	create table if not exists HourlyPrecipitation (
       fromHour timestamp not null,
        maxvalue double,
        minvalue double,
        value double,
        location_name varchar(255) not null,
        primary key (fromHour, location_name)
    );
 
    
    create table if not exists HourlyWeather (
       fromHour timestamp not null,
        highClouds double,
        lowClouds double,
        mediumClouds double,
        cloudiness double,
        dewPoint double,
        globalRadiation double,
        gust double,
        fog double,
        humidity double,
        pressure double,
        temperature double,
        windDirection_deg double,
        windDirection_name varchar(255),
        windSpeed_beaufort integer,
        windSpeed_mps double,
        windSpeed_name varchar(255),
        location_name varchar(255) not null,
        primary key (fromHour, location_name)
    );


    create table if not exists UnknownWV (
       fromHour timestamp not null,
        weatherItem varchar(255) not null,
        location_name varchar(255) not null,
        primary key (fromHour, location_name, weatherItem)
    );

    
    create table if not exists UnknownWV_item (
       UnknownWV_fromHour timestamp not null,
        UnknownWV_location_name varchar(255) not null,
        UnknownWV_weatherItem varchar(255) not null,
        item varchar(255)
    );

    
    create table if not exists UserInformation (
       userName varchar(255) not null,
        encodedPassword varchar(255),
        primary key (userName)
    );

    
    alter table HourlyPrecipitation 
       add constraint if not exists FKrh67reb9xhoo0775ervi0n7tm 
       foreign key (location_name) 
       references Location;

    
    alter table HourlyWeather 
       add constraint if not exists FKlwnjo05pnxo5k6f8s53y0tmtg 
       foreign key (location_name) 
       references Location;

    
    alter table Location 
       add constraint if not exists FK20kfrl1yb9dujp553x6uiutdh 
       foreign key (user_userName) 
       references UserInformation;
       
       
    alter table UnknownWV 
       add constraint if not exists FKnrr32vxiy8rql3hxsqmqqpvfs 
       foreign key (location_name) 
       references Location;
       
    
    alter table UnknownWV_item 
       add constraint if not exists FKc1oe7sqpmcwq3n91j5xetpwib 
       foreign key (UnknownWV_fromHour, UnknownWV_location_name, UnknownWV_weatherItem) 
       references UnknownWV;
    
