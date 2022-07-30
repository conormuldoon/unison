    create table HourlyPrecipitation (
       fromHour timestamp not null,
        maxvalue float8,
        minvalue float8,
        value float8,
        location_name varchar(255) not null,
        primary key (fromHour, location_name)
    ); 
    
    create table HourlyWeather (
       fromHour timestamp not null,
        highClouds float8,
        lowClouds float8,
        mediumClouds float8,
        cloudiness float8,
        dewPoint float8,
        globalRadiation float8,
        gust float8,
        fog float8,
        humidity float8,
        pressure float8,
        temperature float8,
        windDirection_deg float8,
        windDirection_name varchar(255),
        windSpeed_beaufort int4,
        windSpeed_mps float8,
        windSpeed_name varchar(255),
        location_name varchar(255) not null,
        primary key (fromHour, location_name)
    );
    
    create table Location (
       name varchar(255) not null,
        geom GEOMETRY,
        user_userName varchar(255),
        primary key (name)
    );
    
    
    create table UnknownWV (
       fromHour timestamp not null,
        weatherItem varchar(255) not null,
        location_name varchar(255) not null,
        primary key (fromHour, location_name, weatherItem)
    );
 
    
    create table UnknownWV_item (
       UnknownWV_fromHour timestamp not null,
        UnknownWV_location_name varchar(255) not null,
        UnknownWV_weatherItem varchar(255) not null,
        item varchar(255)
    );

    
    create table UserInformation (
       userName varchar(255) not null,
        encodedPassword varchar(255),
        primary key (userName)
    );
    
    alter table HourlyPrecipitation 
       add constraint FKrh67reb9xhoo0775ervi0n7tm 
       foreign key (location_name) 
       references Location; 
    
    alter table HourlyWeather 
       add constraint FKlwnjo05pnxo5k6f8s53y0tmtg 
       foreign key (location_name) 
       references Location;
    
    alter table Location 
       add constraint FK20kfrl1yb9dujp553x6uiutdh 
       foreign key (user_userName) 
       references UserInformation;

    alter table UnknownWV 
       add constraint FKnrr32vxiy8rql3hxsqmqqpvfs 
       foreign key (location_name) 
       references Location;

    alter table UnknownWV_item 
       add constraint FKc1oe7sqpmcwq3n91j5xetpwib 
       foreign key (UnknownWV_fromHour, UnknownWV_location_name, UnknownWV_weatherItem) 
       references UnknownWV;
