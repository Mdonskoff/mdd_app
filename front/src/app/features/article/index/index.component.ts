import {Component, OnDestroy, OnInit} from '@angular/core';
import {mergeMap, Subscription, tap} from "rxjs";
import {ArticleItem} from "../interfaces/article-item.interface";
import {User} from "../../../shared/interfaces/user.interface";
import {ArticleService} from "../services/article.service";
import {Router} from "@angular/router";
import {UserService} from "../../../shared/services/user.service";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})
export class IndexComponent implements OnInit, OnDestroy {
  article$! : Subscription;
  articleItem! : ArticleItem[];
  user!: User | null;
  sortArticleAsc : boolean = true;
  arrow: string = "&#x2B73;"

  constructor(
    private articleService : ArticleService,
    private router: Router,

    private userService : UserService

  ){}

  ngOnInit(): void {
   //récupérer les articles de l'user
    this.article$ = this.userService.getArticles().pipe(
      tap(result => {
        this.articleItem = result.data.Articles
      })
    ).subscribe()
  }

  ngOnDestroy(): void {
    this.article$.unsubscribe();
  }

  create(label : string) {
    if (label.includes("Créer")) {
      this.router.navigate(['article/create']);
    }
  }

  sortArticle(): void{ //fonction sort de JS

    if (this.sortArticleAsc){
      this.sortArticleAsc = !this.sortArticleAsc;
      this.articleItem = this.articleItem.sort(this.sortArticleByCreatedDesc);
      this.arrow = "&#x2B71;"
      return;
    }
    this.arrow = "&#x2B73;"
    this.sortArticleAsc = !this.sortArticleAsc;
    this.articleItem = this.articleItem.sort(this.sortArticleByCreatedAsc);
  }

  sortArticleByCreatedAsc(articleItem : ArticleItem, newArticleItem : ArticleItem): number {
    return new Date(newArticleItem.date!).getTime() - new Date(articleItem.date!).getTime();
  }

  sortArticleByCreatedDesc(articleItem : ArticleItem, newArticleItem : ArticleItem): number {
    return new Date(articleItem.date!).getTime() - new Date(newArticleItem.date!).getTime();
  }
}
