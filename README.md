
## Instructions

### First time setup

Open a terminal in the repository root and run the following script:

```bash
. ./init.sh
```

This will install the necessary dependencies and set up the project.

To execute, run 

```bash
cd demo
sbt
```

Then run 
```bash
run --help
run rank --games ./in/scores.txt --standings ./out/standings-out.txt
run print --games ./in/scores.txt
test
```

