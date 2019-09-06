    create table if not exists GeoDBCoordinates (
       geom blob,
        location_name varchar(255) not null,
        primary key (location_name)
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
        fog double,
        humidity double,
        pressure double,
        temperature double,
        windDirection_deg double not null,
        windDirection_name varchar(255),
        windSpeed_beaufort integer not null,
        windSpeed_mps double not null,
        windSpeed_name varchar(255),
        location_name varchar(255) not null,
        primary key (fromHour, location_name)
    );
    
    create table if not exists LocationDetails (
       name varchar(255) not null,
        uri varchar(255),
        user_userName varchar(255),
        primary key (name)
    );
    
    
    create table if not exists UserInformation (
       userName varchar(255) not null,
        passwordBCrypt varchar(255),
        primary key (userName)
    );
    
    alter table GeoDBCoordinates 
       add constraint if not exists FK7qgeafw7kwbg3h76srydwu1wi 
       foreign key (location_name) 
       references LocationDetails;
       
    alter table HourlyPrecipitation 
       add constraint if not exists FKknxifbv2wongq21fly9yjkeot 
       foreign key (location_name) 
       references LocationDetails;
    
    alter table HourlyWeather 
       add constraint if not exists FK9suxsvjuyvt2x3vi8tgwe7g5a 
       foreign key (location_name) 
       references LocationDetails;
    
    alter table LocationDetails 
       add constraint if not exists FK955mkrhajp4s27eyw7px4nktj 
       foreign key (user_userName) 
       references UserInformation;
       
    