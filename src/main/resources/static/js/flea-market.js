document.addEventListener("DOMContentLoaded", function () {
  fleamarketPosts();
});

function fleamarketPosts() {
  fetch(`/api/fleamarket/list`)
  .then(response => {
    if (!response.ok) {
      throw new Error('에러 발생');
    }
    return response.json();
  })
  .then(data => {
    renderPosts(data);
  })
  .catch(error => console.error('에러 발생', error));
}

function renderPosts(data) {
  const tableBody = document.querySelector("#product-list");
  tableBody.innerHTML = '';

  const template = document.getElementById('article-template');

  data.forEach(post => {
    const clone = document.importNode(template.content, true);
    clone.querySelector(".title").textContent = post.title;
    clone.querySelector(".price").textContent = post.price + " 원";

    tableBody.appendChild(clone);
  });
}