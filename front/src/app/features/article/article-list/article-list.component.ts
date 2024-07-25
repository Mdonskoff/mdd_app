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

  onClick(): void {
    this.router.navigate(['/post/post'], {queryParams : {id : this.articleItem?.id}});
  }

}
