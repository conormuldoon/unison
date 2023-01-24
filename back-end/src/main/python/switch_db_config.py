import switch_list_items

# Enables the database configuration to be switched by Bash scripts between PostgreSQL/PostGIS and H2/GeoDB. 
if __name__ == "__main__":
    matchlist = ["driverClassName", "hibernate.dialect",
                 "datasource.url", "hibernate.ddl"]
    switch_list_items.switchitems(matchlist)
