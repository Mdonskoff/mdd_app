import {Component, ElementRef, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {User} from "../../../shared/interfaces/user.interface";
import {UserService} from "../../../shared/services/user.service";
import {Subscription, tap} from "rxjs";
import {CommentService} from "../services/comment.service";
import {Comment} from "../interfaces/comment.interface";

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.scss']
})
export class CommentsComponent implements OnInit, OnDestroy {

  @Input() idArticle!: string;
  @Input() comments: Comment[] = [];
  @ViewChild('commentContain') commentContain: ElementRef | undefined;
  comments$!: Subscription;
  comment: Comment = {};
  user!: User;
  constructor(
    private commentService: CommentService,
    private userService : UserService
  ){}


  ngOnInit(): void {
    this.comments$ =  this.userService.getMe().pipe(
      tap(result => {
        this.user = result.data.me;
      })
    )
      .subscribe();
  }

  ngOnDestroy(): void {
    this.comments$.unsubscribe();
  }

  onClick(): void {
    if (!this.commentContain?.nativeElement.value)
      return;
    this.comment.comments = this.commentContain?.nativeElement.value;
    this.comment.idArticle = this.idArticle;
    this.comment.pseudo = this.user.pseudo;
    this.commentService.postComment(this.comment).subscribe({
      next: () => {
        this.comments.push(this.comment);
        this.comment = {};
        this.commentContain!.nativeElement.value = "";
      },
    });
  }

}
