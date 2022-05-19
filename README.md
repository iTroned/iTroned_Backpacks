# iTroned_Backpacks
Minecraft Backpack plugin

# Info
Plugin adding simple backpacks with expensive crafting costs (Will be configurable in the future)
The backpacks comes in three tiers where the previous tier is needed in the crafting recipe.
The backpacks are saved to a backpacks.yml file and the backpack closed is saved whenever it is closed
The inventory itself however is also saved in the RAM while the server is running, before replacing the file when all backpacks are saved during a shutdown.
This means that now you can dupe items if the inventory is open and edited and the server gets killed. Trying to find a fix.

The loading is not optimized aswell yet as all the backpacks get loaded into a hashmap on launch. Suggestions welcome :)
Feel free to build upon the code aswell.

Also having trouble saving items with custom nbt, as I have not implemented serializing. Would love some suggestions here!

Would also love some suggestions for new default recipes for the items!

### ** /backpack gives a working tier 1 backpack and needs permission ibackpacks.admin **

## Tier 1
### Recipe
![image](https://user-images.githubusercontent.com/72600583/169407068-400bbb9c-fc89-442a-b3ae-d7d0373c5b6d.png)
### Backpack
![image](https://user-images.githubusercontent.com/72600583/169407810-b6456de0-081d-4643-9c7f-7752cd588015.png)

## Tier 2
### Recipe
![image](https://user-images.githubusercontent.com/72600583/169407248-46b9574f-3087-416c-bfcc-b97bb87f6bac.png)
### Backpack
![image](https://user-images.githubusercontent.com/72600583/169408886-09b95ef2-2622-477a-a7ff-4a8ac7fd16ae.png)

## Tier 3
### Recipe
![image](https://user-images.githubusercontent.com/72600583/169407284-8d9012b5-f249-4395-8760-7dbac4430016.png)
### Backpack
![image](https://user-images.githubusercontent.com/72600583/169407393-06e19070-e7cd-45b7-9356-482b987b190c.png)
