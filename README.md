## Hello World Open (HWO) 2014 Goldwipf Bot

This is the Goldwipf team bot system.

### File Structure And Scripts

In this directory, there are some scripts that you can use to build and run your bot.
On the C.I system and in the competition, your bot will be run on the HWO servers
using these scripts.

- `./build` - build the bot, i.e. prepare for running
- `./run <host> <port> [<botname>]` - run the bot synchronously.

In subdirectories, there are bot implementations in different programming languages. The
`config` file determines which implementation is used. Each language folder contains two
scripts:

- `java/build` builds the Java bot
- `java/run <host> <port> <botname> <botkey>` runs the Java bot

### Logging

The C.I system will capture the standard output of your bot so you can log relevant events
by just writing to stdout. Please don't log all incoming and outgoing messages though, as
that would generate too much data. We'll probably limit the maximum number of lines captured.

### External dependencies

To ensure fast build/run times, the bots are built offline on the HWO servers. This means
that the build process cannot download external dependencies from the Internet. For each
bot template we've included the necessary dependencies (sockets, JSON) either on the
HWO server platform or into this codebase. If you need anything else, you'll need to find a way to
include the extra dependencies into your codebase.

The C.I system will build and run your bot regularly to ensure that it is compatible with
the build process on the HWO servers.

### Configuration

Language, botkey and name are configured in [config](config) file.

### Bot Protocol

See the [Technical specification](https://helloworldopen.com/techspec) online.
