#!/bin/bash
./tools/generate-select-methods.sh 10 'And(self, NoPatterns)' "NoConditions" > /dev/null
cat /tmp/neodsl-select-method*