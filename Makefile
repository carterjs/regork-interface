source = cjs522/*.java cjs522/batch/*.java cjs522/inventory/*.java cjs522/listing/*.java cjs522/product/*.java cjs522/roles/*.java cjs522/supplier/*.java
outfile = cjs522.jar

all: cjs522
cjs522: $(source)
	javac -d ./ $(source)
	jar -cfmv $(outfile) Manifest.txt *.class
	mkdir -p bin
	mv *.class bin
run: cjs522
	java -jar $(outfile)
clean:
	rm -r bin
