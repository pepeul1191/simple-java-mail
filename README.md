# Simple Java Mail

Email template de Cerberus

Reemplazo de string por key:

```
Map<String, String> values = new HashMap<String, String>();
values.put("value", x);
values.put("column", y);
StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
String result = sub.replace("There's an incorrect value '%(value)' in column # %(column)");
```

Ejecutar Main Class usando Maven:

    $ mvn clean && mvn install && mvn exec:java -Dexec.mainClass="configs.App"

---

Fuentes:

+ http://www.simplejavamail.org/#/download
+ https://github.com/bbottema/simple-java-mail
+ https://tedgoas.github.io/Cerberus/
+ https://stackoverflow.com/questions/2286648/named-placeholders-in-string-formatting