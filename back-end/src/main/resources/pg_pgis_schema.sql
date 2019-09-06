    
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
        windDirection_deg float8 not null,
        windDirection_name varchar(255),
        windSpeed_beaufort int4 not null,
        windSpeed_mps float8 not null,
        windSpeed_name varchar(255),
        location_name varchar(255) not null,
        primary key (fromHour, location_name)
    );
    
    create table LocationDetails (
       name varchar(255) not null,
        uri varchar(255),
        user_userName varchar(255),
        primary key (name)
    );
    
    create table PostGISCoordinates (
       geom GEOMETRY,
        location_name varchar(255) not null,
        primary key (location_name)
    );
    
    create table UserInformation (
       userName varchar(255) not null,
        passwordBCrypt varchar(255),
        primary key (userName)
    );
    
    alter table if exists HourlyPrecipitation 
       add constraint FKknxifbv2wongq21fly9yjkeot 
       foreign key (location_name) 
       references LocationDetails;
   
    alter table if exists HourlyWeather 
       add constraint FK9suxsvjuyvt2x3vi8tgwe7g5a 
       foreign key (location_name) 
       references LocationDetails;
       
   alter table if exists LocationDetails 
       add constraint FK955mkrhajp4s27eyw7px4nktj 
       foreign key (user_userName) 
       references UserInformation;
       
   alter table if exists PostGISCoordinates 
       add constraint FKppda1l6ha2ad4ijkrg9i92ve9 
       foreign key (location_name) 
       references LocationDetails;
       
