import switch_list_items

# Switches the settings for the HARMONIE-AROME end-point.
if __name__ == "__main__":
    matchlist = ["api.timezone", "api.uri", "api.fog"]
    switch_list_items.switchitems(matchlist)
