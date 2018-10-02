# Straw tower

## General

This is a freshman project in school I am currently doing with my team. The objectives is to design towers made from straws, some tape, some paper clips and some paper.

The course requires much less but because I got bored so I wanted to discover essentially the best way of building the towers so I'm doing this.

For round one, there are prices associated with specific items, like paper is 0.02$/sheet, tape is 1$/foot, paperclips are 0.02$/piece and straws are 0.01$/piece. The objective is to be the cheapest for a unit height and the tallest

For round two, there are both prices and how much CO2 a specific material emits to make that material. The objective is still unclear.

## Construction

### Basic features

There are a few features that you can see throughout every design.

Straws will be connected together by pinching one straw's end and put that end inside of another straw, like this:

(image awaits)

We sometimes create these specific triangles so as to brace and support our structure, like this:

(image awaits)

To join different straw segments together, we strategically punch several holes through the straws and push the paperclip through to make a connection, like this:

(image awaits)

### Model devised

Currently, we came up with these 2 initial design proposals:

The first looks like this:

![](https://imgur.com/6cKA27r)

Basically a braced pyramid. Pretty straightforward.

And the second looks like this:

![](https://imgur.com/J2zmU3i)

I think this is a better design when it comes to cost because we can really make the bottom base small compared to the pyramid. But for structural stability, the pyramid brings home the cake.

## Analysis

I got a little digging in Ansys so that I can simulate the stresses on the structure as a whole. Because I wanted only a visualization of where the stresses take place, not the actual values of them, I have set up my model as follows:

- Material used: standard structural steel (have roughly the same properties as straws and uncooked spaghetti)
- The beams are 2mm thich in diameter

### Pyramid

- Bottom base is an equilateral triangle. The distance from the triangle's center to its vertex is 15mm
- Middle base the same, with a distance of 10mm
- Top base the same, with a distance of 5mm
- The height between each base is the same and is equal to 30mm
- The total height is 90mm

Here are the results:

#### Equivalent stress

![](https://imgur.com/We5NeoR)

#### Equivalent stress of a cross section

![](https://imgur.com/TmK1sTg)

### Weird pyramid

- Bottom base is an equilateral triangle. The distance from the triangle's center to its vertex is 10mm
- Middle base is the same, with a distance of 5mm (and is flipped upside down)
- Bottom layer height is 50mm
- Top layer height is 40mm
- The total height is 90mm

#### Equivalent stress

![](https://imgur.com/Iedmtsd)

#### Equivalent stress of a cross section

![](https://imgur.com/vuZR2pV)

#### Total deformation

![](https://imgur.com/LEVzX080)

I made them so that they have the same height, so that we can at the very least have something in common to compare.

### StrawModel program

So having known where the stresses are concentrated, we're now more interested in the materials needed, the height each tower model achieves, what is the cost of the tower and what is the cost per unit height. Because all of this is a real hassle to calculate by hand, I have created a program that sits inside this repository called StrawModel.

For anyone who just wants to use it, there is a StrawModel.jar file. Download it, using your shell to nagivate to the folder containing the file, execute ```shell java -jar StrawModel.jar``` then follow the instructions.

For anyone who is interested in the code, you can execure ```gradlew shadowJar``` and then locate the jar inside build/libs. ```gradlew run``` will not work.

Also, if you want a lightweight version of the code that only computes the properties of the tower and not the visualization, you can remove all other files and leave StrawModel.java behind. After that you compile continuously and remove lines where the compiler says have a problem. After a bit of removal it should work perfectly.

This is a snip of what the program is about:

![](https://imgur.com/0IzjpqZ)

This allows you to know the details of the tower, what the height is, what are the materials needed, that sort of stuff. It lets you to experiment with as much variables as you'd like and to really find balance between height and cost.

This project is still ongoing and I will update for you guys more details soon.