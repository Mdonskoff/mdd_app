import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";
import {ArticleItem} from "../interfaces/article-item.interface";

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss']
})
export class ArticleListComponent {

  @Input()  articleItem: ArticleItem | undefined;

  constructor(private router: Router){}

  //route en lien avec article.component.ts
  onClick(): void {
    this.router.navigate(['/article/article'], {queryParams : {id : this.articleItem?.id}});
  }

}
