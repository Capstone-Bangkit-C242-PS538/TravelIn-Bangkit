FROM node:18

WORKDIR /src

COPY package*.json ./

RUN npm install

COPY . .

ENV PORT=5423


CMD [ "npm", "run", "start"]
