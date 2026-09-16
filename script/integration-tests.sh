#!/bin/bash
set -e

BASE_URL="http://127.0.0.1:8088"

TIMESTAMP=$(date +%s)
USERNAME="testuser_$TIMESTAMP"
EMAIL="test_$TIMESTAMP@test.com"
PASSWORD="Test1234!"

echo "=== Test création utilisateur ==="

STATUS=$(curl -s \
  -o /tmp/register-response.json \
  -w "%{http_code}" \
  -X POST "$BASE_URL/api/auth/register" \
  -H "Content-Type: application/json" \
  -d "{
    \"username\": \"$USERNAME\",
    \"email\": \"$EMAIL\",
    \"password\": \"$PASSWORD\"
  }")

echo "HTTP status: $STATUS"
cat /tmp/register-response.json
echo

if [ "$STATUS" -ne 200 ] && [ "$STATUS" -ne 201 ]; then
    echo "Création utilisateur échouée"
    exit 1
fi

echo "Utilisateur créé avec succès"
