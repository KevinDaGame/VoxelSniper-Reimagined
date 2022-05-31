
VoxelSniper
============
The premier long-distance brush editor for [Bukkit](https://bukkit.org/), [Spigot](https://www.spigotmc.org/), [Paper](https://papermc.io/), and most Bukkit-based server distributions. The plugin is fully functional (according to our limited usage), and should be able to snipe most blocks. Please submit an issue if something is not working and I'll try my best to work it out.

I'm thinking of renaming the project to prevent confusion with the already existing VoxelSniper. Please suggest any in the issues section!

Discord 
-------

We have a discord server!
https://discord.com/invite/YDjykYsAFF

Minecraft Version
------------------
VoxelSniper is being built against the Bukkit 1.18.1 API.

***We will not support anything below 1.16.5***

 - Minecraft 1.18

Compilation
-----------
Pre-compiled JARs will be available in releases..

We use Maven to handle dependencies.

- Install [Maven 3][Maven]
- Check out this repository.
- Run `mvn clean package`

Alternatively, use a Java IDE that supports Maven. Please ensure that Maven `version >= 3.2.3` because [Maven Central is now on HTTPS](https://blog.sonatype.com/central-repository-moving-to-https); any version older than 3.2.3 will fail to compile as it will not be able to retrieve sources from Maven Central.

Issue Tracker Notes
-------------------

How do I create a ticket the right way?

- Seperate your reports. You think there is something wrong, but also want this new brush? Make life easier for us and create two tickets. We'd appriciate it big times.
- Don't tell us your story of life. We want facts and information. The more information about `the Problem` you give us, the easier it is for us to figure out what's wrong.
- Check the closed tickets first. Maybe someone created a similiar ticket already. If you think it's unresolved, then give us more information on there instead.

### Bug Report

Make sure to not tell us your story of life. We want brief descriptions of what's wrong to get directly to fixing.
Also try to make the title describe briefly what's wrong and attach things like logs or screenshots to help illustrate the issue further.

Here is an example, where an imaginary brush that should create a ball, creates a cube:

Title: `Brush A creates Cube instead of Ball`

```
Expected behaviour:
Brush A should create a ball with radius 5 when I set it to brush A with brush size 5.

Experienced behaviour:
Brush A created a cube instead.

Additional Information:
CraftBukkit 1.3.2-R1.0
VoxelSniper 5.166.11
java -version output:
java version "1.7.0_07"
Java(TM) SE Runtime Environment (build 1.7.0_07-b11)
Java HotSpot(TM) Client VM (build 23.3-b01, mixed mode)
```

Additional Information like what java version the server runs on would be appriciated, but is not required at all times.

### Enhancement Request

This is where imagination comes in, but make sure to keep as it easy for us. As mentioned, we don't want your story of life. We want to know what you think would be a good enhancement.

Here is an example of an enhancement request.

Title: `Brush that creates lines`

```
Enhancement Proposal:
Creating a brush that creates a line.

Suggested usage:
You click two points with your arrow and it will create a line with blocks.

Reason of proposal:
It would be useful, since off angle lines are sometimes hard to make.
```

Keep in mind that those are guidelines.
We will still look into stuff that does not follow these guidlines, but it will give you an idea what we want in a ticket and make our life easier.

Pull Requests
-------------

We do accept pull requests and enhancements from third parties. Guidelines how to submit your pull requests properly and how to format your code will come.

Some rough guidelines for now:

- Keep the number of commits to a minimum. We want to look over the commit and basically see what you've done.
- Coding guidelines should try to comply to the checkstyle rules (checkstyle.xml) but not blindly. Use your mind to make smart decissions.
- Give us a good description to what you've done.
- Try to submit one change in one pull request and try to link it to the issue in the tracker if possible.

[VoxelSniperWiki]: http://voxelwiki.com/minecraft/VoxelSniper/
[JenkinsJob]: http://ci.thevoxelbox.com/job/VoxelSniper/
[Bukkit]: http://bukkit.org/
[Maven]: http://maven.apache.org/
