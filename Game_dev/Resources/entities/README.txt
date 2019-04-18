//This will explain the file format
//EntityAmount=amount of entities to load in this file
//EntitySpawnX=entity x spawn location in pixels
//EntitySpawnY=entity y spawn location in pixels
//EntitySpawnDirection=entity spawn direction 0=up,1=right,2=down,3=left,any other int=down.
	0
   3 1
	2
//Note that if the entity direction is not used/needed a placeholder such as 0 should be used.
//All text inside a file will be read as int and so all text in a file should be an int.
//In addition do not attempt to load this file
//Tile width and height is currently set to 64 pixels;
//format layout is as follows
//////////////////////////////////////////////////////////////////////////////////////
EntityAmount

Entity1Type Entity1SpawnX Entity1SpawnY Entity1SpawnDirection
Entity2Type Entity2SpawnX Entity2SpawnY Entity2SpawnDirection
Entity3Type Entity3SpawnX Entity3SpawnY Entity3SpawnDirection
///////////////////////////////////////////////////////////////////////
Example
///////////////////////////////////////////////////////////////////////
5

0 100 100 0
0 200 200 0
0 300 100 0
1 700 600 0
2 300 400 2
///////////////////////////////////////////////////////////////////////
