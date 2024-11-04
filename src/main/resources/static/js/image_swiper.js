let currentImageIndex = 0;
const images = document.querySelectorAll('#roomImages img');

function showImage(index) {
  images.forEach((img, i) => {
    img.style.transform = `translateX(-${index * 100}%)`;
  });
}

function nextImage() {
  if (currentImageIndex < images.length - 1) {
    currentImageIndex++;
    showImage(currentImageIndex);
  }
}

function prevImage() {
  if (currentImageIndex > 0) {
    currentImageIndex--;
    showImage(currentImageIndex);
  }
}