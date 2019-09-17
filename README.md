Regork Database Interface
=========================
Author: Carter Schmalzle (cjs522)

This program is an interface for the Regork 
database. Regork staff and suppliers can use it 
to view and update data related to products and 
their sources.

Running The Program
===================
The folder cjs522 contains the Java source 
code, but it is organized with folders, so a 
simple "javac *.java" won't do it.

For your convenience, I included a Makefile 
to build the JAR file and stow the classes in the 
bin directory.

Commands: 
- "java -jar cjs522.jar" : runs the program
- "make" : builds the program and fills bin with class files
- "make run" : same as make, but also runs it
- "make clean" : deletes the class files from 
    the bin folder

General Project Information
===========================
This project is built around a page component 
called ActionPage. Instances of ActionPage store 
their action in the "run" method and store a list 
of actions to display as options. Selecting and 
running actions is handled by this class and can 
be overridden by child classes. This means that 
it is extremely easy to add new pages and 
organize pages by feature.

The only potential problem is that there are 
so many pages that it can be easy to get lost or 
disoriented. When in doubt, just keep selecting 0 
(back or exit).

There is a lot to see here. It is possible to 
view just about everything in the datbase. To 
find things such as batches which always belong 
to a specific product, search under the product 
menu.

Depending on the role you choose, you will be 
presented with slightly different menus. While 
many of the pages are the same, suppliers are 
directed more easily to products and batches made 
by their company, and while managers can update 
some information, regular Regork employees are 
only permitted to view. 

What to Check Out
=================
Recalls can be viewed from the inventory 
list, a product's batch list, or on the detail 
pages of a product batch or inventory shipment. 
If a batch is recalled or at risk, it is noted 
whether or not it is that batch or a batch of 
products that it includes. Recalls state can be 
changed in the menu called "Recalls" on the batch 
detail page, but only if you are viewing it as a 
manager or a supplier. Hint: Tree Juice in batch 
#4 includes some Pulp from batch #6 that is made 
from the Copy Paper also in batch #8. If you 
change the recall state of Copy Paper in batch 
#8, you'll see different warnings whenever 
looking at the Tree Juice. Yay for transitivity!

Batches can be created from a product's batch 
list, but only if you are a supplier. 
Included/dependent batches can also be added if 
you are a supplier.

You can view all of the batches within a 
certain batch which is done through a cool 
recursive SQL query. When viewing included 
batches, you can also jump to the detail view on 
one of those batches, then once you're done, you 
can go back to the list of included batches.

As I said, there's really a lot to see within 
these menus. Just exploring is the best way to 
appreciate it.

Getting Familiar with the Database (and the data)
================================================
- A listing is a generic product type. I 
    could be selling Paper from multiple batches or 
    even multiple suppliers, but it'll have the same 
    listing name and price.
- Inventory is really just a list of 
    shipments. Each shipment contains one batch of 
    products that will be labeled as a certain listing
- Paper is made by Dunder Mifflin
- Beet-related products are made by Schrute 
    Farms