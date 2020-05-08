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
