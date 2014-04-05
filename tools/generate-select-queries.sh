#!/bin/bash
OUT_BASE_PATH=src/main/scala/org/neodsl/dsl/queries/SelectQuery
SELECT_QUERY_TEMPLATE_FILE=src/main/templates/org.neodsl.dsl.queries/SelectQuery.mustache
GENERATE_CMD=tools/generate-source.sh
TYPE_NAMES=(A B C D E F G H I J K L M N O P Q R S T W X Y Z)
TMP_DATA_FILE=/tmp/neodsl-template-data.yml
N=$1
i=0

function buildDataFile {
  echo "---" > $TMP_DATA_FILE
  echo "n: $i" >> $TMP_DATA_FILE
  echo -n "types: [" >> $TMP_DATA_FILE
    
  for (( j = 1; j <= $i; j++ )) ; do
    TYPE_NAME=${TYPE_NAMES[$j-1]} 
    echo -n " { name: \"$TYPE_NAME\"" >> $TMP_DATA_FILE
    if [ $j == $i ]; then
      echo -n ", isLast: true } " >> $TMP_DATA_FILE
    else
      echo -n " }," >> $TMP_DATA_FILE
    fi
  done

  echo "]" >> $TMP_DATA_FILE
  echo "---" >> $TMP_DATA_FILE
  echo >> $TMP_DATA_FILE
}

for (( i=1; $i <= $N; i++ )) ; do
  buildDataFile
  echo "Generating $OUT_BASE_PATH$i.scala"
  $GENERATE_CMD "$SELECT_QUERY_TEMPLATE_FILE" "$TMP_DATA_FILE" "$OUT_BASE_PATH$i.scala" 
done
