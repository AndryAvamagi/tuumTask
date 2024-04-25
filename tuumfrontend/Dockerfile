FROM node:latest
WORKDIR /app


copy package.json .

run yarn

COPY . .

run yarn build

CMD ["yarn", "start"]
