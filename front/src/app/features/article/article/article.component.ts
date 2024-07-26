import {Component, OnDestroy, OnInit} from '@angular/core';
import {ArticleItem} from "../interfaces/article-item.interface";
import {ActivatedRoute} from "@angular/router";
import {ArticleService} from "../services/article.service";
import {mergeMap, Subscription, tap} from "rxjs";
import {Topic} from "../../../shared/interfaces/topic.interface";

//Aller sur un article
@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit, OnDestroy {

  articleItem!: ArticleItem;
  articleItem$! : Subscription;
  topic: Topic = {id : "0", description : "", label : ""};

  constructor(
    private route: ActivatedRoute,
    private articleService : ArticleService,
  ){}


  ngOnInit(): void {
    this.articleItem$ = this.route.queryParams.pipe(
      mergeMap(param => {
        console.log(param['id'])
        return this.articleService.getArticleById(param['id']);
      }),
      tap(result => {
        this.articleItem = result.data.article;
        console.log(this.topic)
        console.log(result)
        this.topic.id = result.data.article.idTopic!.toString()
        this.topic.label = result.data.article.labelTopic!
      })
    ).subscribe(
      {
        error: err => {
          console.log(err)
        }
      }
    );

  }
  ngOnDestroy(): void {
    this.articleItem$.unsubscribe();
  }

}
