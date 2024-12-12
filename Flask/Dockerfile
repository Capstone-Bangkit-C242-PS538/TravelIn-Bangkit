# Gunakan image Python sebagai base image
FROM python:3.9-slim

# Set work directory di dalam container
WORKDIR /app

# Salin semua file dari direktori lokal ke dalam container
COPY . .

# Install semua dependensi yang terdaftar di requirements.txt
RUN pip install -r requirements.txt

# Set environment variable untuk Flask
ENV FLASK_APP=app.py
ENV FLASK_RUN_HOST=0.0.0.0
ENV FLASK_RUN_PORT=5634

# Ekspos port 5634
EXPOSE 5634

# Jalankan Flask
CMD ["flask", "run"]