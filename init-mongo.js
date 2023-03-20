db.createUser(
  {
    user: 'dnd',
    pwd: '12345678',
    roles: [{ role: 'readWrite', db: 'dnd_db' }],
  },
);
