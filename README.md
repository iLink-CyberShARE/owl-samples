Maven Command List:

// create empty maven project with quick start skeleton
> mvn archetype:generate "-DgroupId=cybershare.utep.edu" "-DartifactId=owl-sample-1" "-DinteractiveMode=false"

// clean package build from scratch
> mvn clean package

// run the App.java
go to target/classes
>java cybershare.utep.edu.App


References:

https://github.com/owlcs/owlapi/wiki/Documentation

https://github.com/phillord/owl-api/blob/master/contract/src/test/java/org/coode/owlapi/examples/Examples.java