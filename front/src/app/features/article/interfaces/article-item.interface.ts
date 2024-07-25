import {Comment} from "./comment.interface";

export interface ArticleItem {
  id?: string
  title?: string
  contents?: string
  idTopic?: string
  username?: string
  commentsList?: Comment[]
  date?: Date

}
