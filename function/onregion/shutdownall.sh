. ./setenv.sh

gfsh -e "connect --locator=localhost[12345]" -e "shutdown --include-locators=true"